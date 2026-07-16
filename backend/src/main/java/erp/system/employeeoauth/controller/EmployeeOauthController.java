package erp.system.employeeoauth.controller;

import erp.system.employeeoauth.dto.EmployeeOauthCreateRequest;
import erp.system.employeeoauth.dto.EmployeeOauthResponse;
import erp.system.employeeoauth.service.EmployeeOauthService;
import erp.system.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-oauths")
@RequiredArgsConstructor
public class EmployeeOauthController {
    private final EmployeeOauthService employeeOauthService;

    @GetMapping
    public List<EmployeeOauthResponse> getByEmployee(@RequestParam Long employeeId) {
        SecurityUtils.checkOwnerOrAdmin(employeeId);
        return employeeOauthService.getByEmployee(employeeId);
    }


    @PostMapping
    public ResponseEntity<EmployeeOauthResponse> link(@Valid @RequestBody EmployeeOauthCreateRequest request) {
        SecurityUtils.checkOwnerOrAdmin(request.employeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeOauthService.link(request));
    }

    @DeleteMapping("/{employeeOauthId}")
    public ResponseEntity<Void> unlink(@PathVariable Long employeeOauthId, @RequestParam Long employeeId) {
        SecurityUtils.checkOwnerOrAdmin(employeeId);
        employeeOauthService.unlink(employeeOauthId, employeeId);
        return ResponseEntity.noContent().build();
    }
}
