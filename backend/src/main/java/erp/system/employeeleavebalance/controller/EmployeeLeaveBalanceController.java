package erp.system.employeeleavebalance.controller;


import erp.system.employeeleavebalance.dto.EmployeeLeaveBalanceCreateRequest;
import erp.system.employeeleavebalance.dto.EmployeeLeaveBalanceResponse;
import erp.system.employeeleavebalance.service.EmployeeLeaveBalanceService;
import erp.system.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-leave-balances")
@RequiredArgsConstructor
public class EmployeeLeaveBalanceController {

    private final EmployeeLeaveBalanceService employeeLeaveBalanceService;

    @GetMapping
    public List<EmployeeLeaveBalanceResponse> getByEmployee(@RequestParam Long employeeId) {
        SecurityUtils.checkOwnerOrAdmin(employeeId);
        return employeeLeaveBalanceService.getByEmployee(employeeId);
    }

    @PostMapping
    public ResponseEntity<EmployeeLeaveBalanceResponse> create(@Valid @RequestBody EmployeeLeaveBalanceCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeLeaveBalanceService.create(request));
    }
}
