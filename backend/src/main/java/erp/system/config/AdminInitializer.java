package erp.system.config;

import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${admin.employee-no}")
    private String adminEmployeeNo;

    @Value("${admin.initial-password}")
    private String adminInitialPassword;

    @Override
    public void run(String... args) {
        if (employeeRepository.existsByEmployeeNo(adminEmployeeNo)) {
            return;
        }
        Employee admin = Employee.builder()
                .employeeNo(adminEmployeeNo)
                .name("관리자")
                .password(passwordEncoder.encode(adminInitialPassword))
                .role(Employee.ROLE_ADMIN)
                .build();
        employeeRepository.save(admin);
    }
}
