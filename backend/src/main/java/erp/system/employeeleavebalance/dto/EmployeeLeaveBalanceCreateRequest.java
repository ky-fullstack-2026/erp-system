package erp.system.employeeleavebalance.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeLeaveBalanceCreateRequest(
        @NotNull(message = "사원 ID는 필수입니다.") Long employeeId,
        @NotNull(message = "휴가종류 ID는 필수입니다.") Long leaveTypeId,
        @NotNull(message = "총 부여일수는 필수입니다.") @DecimalMin(value = "0.0") BigDecimal totalDays,
        LocalDate expireDate
) {
}
