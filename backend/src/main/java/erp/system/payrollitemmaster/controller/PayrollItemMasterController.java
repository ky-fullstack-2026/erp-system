package erp.system.payrollitemmaster.controller;


import erp.system.payrollitemmaster.dto.PayrollItemMasterRequest;
import erp.system.payrollitemmaster.dto.PayrollItemMasterResponse;
import erp.system.payrollitemmaster.service.PayrollItemMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll-item-masters")
@RequiredArgsConstructor
public class PayrollItemMasterController {

    private final PayrollItemMasterService service;

    @GetMapping
    public List<PayrollItemMasterResponse> getAll() { return service.getAll(); }

    @PostMapping
    public ResponseEntity<PayrollItemMasterResponse> create(@Valid @RequestBody PayrollItemMasterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public PayrollItemMasterResponse update(@PathVariable Long id, @Valid @RequestBody PayrollItemMasterRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
