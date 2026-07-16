package erp.system.leaverequest.controller;

import erp.system.leaverequest.dto.LeaveRequestApproveRequest;
import erp.system.leaverequest.dto.LeaveRequestCreateRequest;
import erp.system.leaverequest.dto.LeaveRequestResponse;
import erp.system.leaverequest.service.LeaveRequestService;
import erp.system.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping("/{id}")
    public LeaveRequestResponse getById(@PathVariable Long id) {
        LeaveRequestResponse response = leaveRequestService.getById(id);
        SecurityUtils.checkOwnerOrAdmin(response.employeeId());
        return response;
    }

    @GetMapping
    public List<LeaveRequestResponse> getByEmployee(@RequestParam Long employeeId) {
        SecurityUtils.checkOwnerOrAdmin(employeeId);
        return leaveRequestService.getByEmployee(employeeId);
    }

    @PostMapping
    public ResponseEntity<LeaveRequestResponse> create(@Valid @RequestBody LeaveRequestCreateRequest request) {
        SecurityUtils.checkOwnerOrAdmin(request.employeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequestService.create(request));
    }
    @PatchMapping("/{id}/approve")
    public LeaveRequestResponse approve(@PathVariable Long id, @Valid @RequestBody LeaveRequestApproveRequest request) {
        return leaveRequestService.approve(id, request.approverId());
    }

    @PatchMapping("/{id}/reject")
    public LeaveRequestResponse reject(@PathVariable Long id, @Valid @RequestBody LeaveRequestApproveRequest request) {
        return leaveRequestService.reject(id, request.approverId());
    }

    @PatchMapping("/{id}/cancel")
    public LeaveRequestResponse cancel(@PathVariable Long id) {
        LeaveRequestResponse existing = leaveRequestService.getById(id);
        SecurityUtils.checkOwnerOrAdmin(existing.employeeId());
        return leaveRequestService.cancel(id);
    }

}
