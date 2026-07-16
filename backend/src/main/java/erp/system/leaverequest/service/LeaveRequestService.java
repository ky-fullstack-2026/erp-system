package erp.system.leaverequest.service;


import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.leaverequest.dto.LeaveRequestCreateRequest;
import erp.system.leaverequest.dto.LeaveRequestResponse;
import erp.system.leaverequest.entity.LeaveRequest;
import erp.system.leavetype.entity.LeaveType;
import erp.system.leaverequest.repository.LeaveRequestRepository;
import erp.system.leavetype.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveRequestService {


    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;


    public LeaveRequestResponse getById(Long leaveRequestId) {
        return LeaveRequestResponse.from(findActive(leaveRequestId));
    }

    public List<LeaveRequestResponse> getByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(LeaveRequestResponse::from)
                .toList();
    }

    @Transactional
    public LeaveRequestResponse create(LeaveRequestCreateRequest request) {
        Employee employee = resolveEmployee(request.employeeId());
        LeaveType leaveType = resolveLeaveType(request.leaveTypeId());

        LeaveRequest leaveRequest = new LeaveRequest(
                employee,
                leaveType,
                request.startDate(),
                request.endDate(),
                request.leaveDays(),
                request.reason()
        );

        return LeaveRequestResponse.from(leaveRequestRepository.save(leaveRequest));
    }
    @Transactional
    public LeaveRequestResponse approve(Long leaveRequestId, Long approverId) {
        LeaveRequest leaveRequest = findActive(leaveRequestId);
        leaveRequest.approve(approverId);
        return LeaveRequestResponse.from(leaveRequest);
    }
    @Transactional
    public LeaveRequestResponse cancel(Long leaveRequestId) {
        LeaveRequest leaveRequest = findActive(leaveRequestId);
        leaveRequest.cancel();
        return LeaveRequestResponse.from(leaveRequest);
    }
    @Transactional
    public LeaveRequestResponse reject(Long leaveRequestId, Long approverId) {
        LeaveRequest leaveRequest = findActive(leaveRequestId);
        leaveRequest.reject(approverId);
        return LeaveRequestResponse.from(leaveRequest);
    }

    private LeaveRequest findActive(Long leaveRequestId) {
        return leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LEAVE_REQUEST_NOT_FOUND));
    }
    private Employee resolveEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    private LeaveType resolveLeaveType(Long leaveTypeId) {
        return leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LEAVE_TYPE_NOT_FOUND));
    }


}
