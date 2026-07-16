package erp.system.employeeleavebalance.dto;

import erp.system.employeeleavebalance.entity.EmployeeLeaveBalance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeLeaveBalanceResponse(
        Long employeeLeaveBalanceId,
        Long employeeId,
        String employeeName,
        Long leaveTypeId,
        String leaveTypeName,
        BigDecimal totalDays,
        BigDecimal usedDays,
        BigDecimal remainDays,
        LocalDate expireDate
) {
    public static EmployeeLeaveBalanceResponse from(EmployeeLeaveBalance balance) {
        return new EmployeeLeaveBalanceResponse(
                balance.getEmployeeLeaveBalanceId(),
                balance.getEmployee().getEmployeeId(),
                balance.getEmployee().getName(),
                balance.getLeaveType().getLeaveTypeId(),
                balance.getLeaveType().getLeaveTypeName(),
                balance.getTotalDays(),
                balance.getUsedDays(),
                balance.getRemainDays(),
                balance.getExpireDate()
        );
    }
}
