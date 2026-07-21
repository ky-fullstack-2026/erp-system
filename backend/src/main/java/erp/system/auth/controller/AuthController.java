package erp.system.auth.controller;


import erp.system.auth.dto.LoginRequest;
import erp.system.auth.dto.LoginResponse;
import erp.system.auth.service.AuthService;
import erp.system.employee.dto.EmployeeResponse;
import erp.system.employee.service.EmployeeService;
import erp.system.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public EmployeeResponse me() {
        return employeeService.getById(SecurityUtils.currentEmployeeId());
    }

}
