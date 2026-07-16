package erp.system.leavetype.dto;

import erp.system.leavetype.entity.LeaveType;

import java.math.BigDecimal;

public record LeaveTypeResponse(
        Long leaveTypeId,
        String leaveTypeName,
        boolean paidYn,
        BigDecimal defaultDays,
        String note
) {
    public static LeaveTypeResponse from(LeaveType leaveType) {
        return new LeaveTypeResponse(
                leaveType.getLeaveTypeId(),
                leaveType.getLeaveTypeName(),
                leaveType.isPaidYn(),
                leaveType.getDefaultDays(),
                leaveType.getNote()
        );
    }
}
