package erp.system.security;

import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override

    public UserDetails loadUserByUsername(String loginId) {
        Employee employee = employeeRepository.findByEmployeeNoOrEmail(loginId, loginId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다."));
        return new CustomUserDetails(employee);
    }

}
