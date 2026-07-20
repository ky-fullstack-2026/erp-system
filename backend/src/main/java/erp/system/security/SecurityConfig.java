package erp.system.security;

import erp.system.security.jwt.JwtAuthenticationFilter;
import erp.system.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/api/departments/**", "/api/positions/**",
                                "/api/employment-types/**", "/api/leave-types/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/departments/**", "/api/positions/**",
                                "/api/employment-types/**", "/api/leave-types/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/departments/**", "/api/positions/**",
                                "/api/employment-types/**", "/api/leave-types/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers("/api/payrolls/**").hasRole("ADMIN")
                        .requestMatchers("/api/payroll-details/**").hasRole("ADMIN")
                        .requestMatchers("/api/attendance/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/leave-requests/*/approve", "/api/leave-requests/*/reject").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employee-leave-balances/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/payroll-item-masters/**").hasRole("ADMIN")
                        .requestMatchers("/api/payrolls/**").hasRole("ADMIN")
                        .requestMatchers("/api/payroll-details/**").hasRole("ADMIN")
                        .requestMatchers("/api/attendance/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
