package erp.system.payroll.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PayrollConfirmRequest (
        @NotNull(message = "지급일은 필수입니다.") LocalDate paymentDate
){
}
