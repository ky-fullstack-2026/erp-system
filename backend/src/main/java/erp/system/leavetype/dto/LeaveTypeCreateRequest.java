package erp.system.leavetype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LeaveTypeCreateRequest(
        @NotBlank(message = "휴가종류명은 필수입니다.") String leaveTypeName,
        @NotNull(message = "유급 여부는 필수입니다.") Boolean paidYn,
        BigDecimal defaultDays,
        String note
) {
}
