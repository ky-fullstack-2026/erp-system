package erp.system.auth.service;

import erp.system.auth.dto.LoginRequest;
import erp.system.auth.dto.LoginResponse;
import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.security.CustomUserDetails;
import erp.system.security.jwt.JwtTokenProvider;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.loginId(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        var employee = userDetails.getEmployee();

        if (!employee.isLoginable()) {
            throw new BusinessException(ErrorCode.ACCOUNT_INACTIVE);
        }

        String accessToken = jwtTokenProvider.generateToken(employee.getEmployeeId(), employee.getEmployeeNo(), employee.getRole());
        return LoginResponse.of(accessToken, employee);
    }
}
