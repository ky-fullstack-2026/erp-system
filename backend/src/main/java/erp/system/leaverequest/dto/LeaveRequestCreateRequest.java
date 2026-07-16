package erp.system.leaverequest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LeaveRequestCreateRequest(
        @NotNull(message = "사원 ID는 필수입니다.") Long employeeId,
        @NotNull(message = "휴가종류 ID는 필수입니다.") Long leaveTypeId,
        @NotNull(message = "시작일은 필수입니다.") LocalDate startDate,
        @NotNull(message = "종료일은 필수입니다.") LocalDate endDate,
        @NotNull(message = "사용일수는 필수입니다.") @DecimalMin(value = "0.5") BigDecimal leaveDays,
        @Size(max = 1000) String reason
) {
}
