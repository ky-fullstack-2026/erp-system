package erp.system.leaverequest.dto;

import erp.system.leaverequest.entity.LeaveRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LeaveRequestResponse(
        Long leaveRequestId,
        Long employeeId,
        String employeeName,
        Long leaveTypeId,
        String leaveTypeName,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal leaveDays,
        String reason,
        String status,
        Long approverId
) {
    public static LeaveRequestResponse from(LeaveRequest leaveRequest) {
        return new LeaveRequestResponse(
                leaveRequest.getLeaveRequestId(),
                leaveRequest.getEmployee().getEmployeeId(),
                leaveRequest.getEmployee().getName(),
                leaveRequest.getLeaveType().getLeaveTypeId(),
                leaveRequest.getLeaveType().getLeaveTypeName(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getLeaveDays(),
                leaveRequest.getReason(),
                leaveRequest.getStatus(),
                leaveRequest.getApproverId()
        );
    }
}
