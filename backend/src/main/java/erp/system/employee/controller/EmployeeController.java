package erp.system.employee.controller;

import erp.system.employee.dto.EmployeeCreateRequest;
import erp.system.employee.dto.EmployeeResponse;
import erp.system.employee.dto.EmployeeUpdateRequest;
import erp.system.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(request));
    }
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest request) {
        return employeeService.update(id, request);
    }

}
