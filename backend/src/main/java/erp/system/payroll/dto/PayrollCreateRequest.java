package erp.system.payroll.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record PayrollCreateRequest(
        @NotNull(message = "사원 ID는 필수입니다.") Long employeeId,
        @NotNull(message = "귀속연월은 필수입니다.") @Pattern(regexp = "\\d{6}", message = "귀속연월은 YYYYMM 형식이어야 합니다.") String payrollYearMonth,
        @NotNull(message = "지급총액은 필수입니다.") @DecimalMin(value = "0.0") BigDecimal totalPayAmount,
        @NotNull(message = "공제총액은 필수입니다.") @DecimalMin(value = "0.0") BigDecimal totalDeductionAmount
) {
}
