package erp.system.payroll.controller;


import erp.system.payroll.dto.PayrollConfirmRequest;
import erp.system.payroll.dto.PayrollCreateRequest;
import erp.system.payroll.dto.PayrollResponse;
import erp.system.payroll.service.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    public List<PayrollResponse> getByEmployee(@RequestParam Long employeeId) {
        return payrollService.getByEmployee(employeeId);
    }

    @PostMapping
    public ResponseEntity<PayrollResponse> create(@Valid @RequestBody PayrollCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollService.create(request));
    }

    @PatchMapping("/{payrollId}/confirm")
    public PayrollResponse confirm(@PathVariable Long payrollId, @Valid @RequestBody PayrollConfirmRequest request) {
        return payrollService.confirm(payrollId, request);
    }
}
