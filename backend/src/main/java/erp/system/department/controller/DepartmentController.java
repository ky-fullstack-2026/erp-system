package erp.system.department.controller;

import erp.system.department.dto.DepartmentRequest;
import erp.system.department.dto.DepartmentResponse;
import erp.system.department.dto.DepartmentTreeNode;
import erp.system.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponse> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/tree")
    public List<DepartmentTreeNode> getTree() {
        return departmentService.getTree();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }
    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(request));
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        return departmentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
