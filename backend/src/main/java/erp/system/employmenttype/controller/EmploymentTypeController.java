package erp.system.employmenttype.controller;

import erp.system.employmenttype.dto.EmploymentTypeRequest;
import erp.system.employmenttype.dto.EmploymentTypeResponse;
import erp.system.employmenttype.service.EmploymentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employment-types")
@RequiredArgsConstructor
public class EmploymentTypeController {
    private final EmploymentTypeService employmentTypeService;

    @GetMapping
    public List<EmploymentTypeResponse> findAll() {
        return employmentTypeService.findAll();
    }

    @GetMapping("/{id}")
    public EmploymentTypeResponse getById(@PathVariable Long id) {
        return employmentTypeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<EmploymentTypeResponse> create(@Valid @RequestBody EmploymentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employmentTypeService.create(request));
    }
    @PutMapping("/{id}")
    public EmploymentTypeResponse update(@PathVariable Long id, @Valid @RequestBody EmploymentTypeRequest request) {
        return employmentTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employmentTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
