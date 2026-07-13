package erp.system.position.controller;

import erp.system.position.dto.PositionRequest;
import erp.system.position.dto.PositionResponse;
import erp.system.position.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;
    @GetMapping
    public List<PositionResponse> findAll() {
        return positionService.findAll();
    }

    @GetMapping("/{id}")
    public PositionResponse getById(@PathVariable Long id) {
        return positionService.getById(id);
    }

    @PostMapping
    public ResponseEntity<PositionResponse> create(@Valid @RequestBody PositionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(positionService.create(request));
    }

    @PutMapping("/{id}")
    public PositionResponse update(@PathVariable Long id, @Valid @RequestBody PositionRequest request) {
        return positionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
