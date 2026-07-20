package erp.system.payrollitemmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PayrollItemMasterRequest(
        @NotBlank(message = "항목명은 필수입니다.") @Size(max = 100) String itemName,
        @NotBlank(message = "항목 유형은 필수입니다.") String itemTypeCode,
        @NotNull(message = "과세 여부는 필수입니다.") Boolean taxable,
        @NotNull(message = "고정 여부는 필수입니다.") Boolean fixed
) {
}
