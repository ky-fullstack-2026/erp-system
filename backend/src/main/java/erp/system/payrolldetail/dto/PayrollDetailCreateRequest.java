package erp.system.payrolldetail.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PayrollDetailCreateRequest(
        @NotNull(message = "급여 ID는 필수입니다.") Long payrollId,
        @NotNull(message = "급여항목 ID는 필수입니다.") Long payrollItemMasterId,
        @NotNull(message = "금액은 필수입니다.") BigDecimal amount

) {
}
