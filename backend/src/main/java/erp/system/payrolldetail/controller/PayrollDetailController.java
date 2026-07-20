package erp.system.payrolldetail.controller;

import erp.system.payrolldetail.dto.PayrollDetailCreateRequest;
import erp.system.payrolldetail.dto.PayrollDetailResponse;
import erp.system.payrolldetail.service.PayrollDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll-details")
@RequiredArgsConstructor
public class PayrollDetailController {

    private final PayrollDetailService payrollDetailService;

    @GetMapping
    public List<PayrollDetailResponse> getByPayroll(@RequestParam Long payrollId) {
        return payrollDetailService.getByPayroll(payrollId);
    }

    @PostMapping
    public ResponseEntity<PayrollDetailResponse> create(@Valid @RequestBody PayrollDetailCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollDetailService.create(request));
    }
}
