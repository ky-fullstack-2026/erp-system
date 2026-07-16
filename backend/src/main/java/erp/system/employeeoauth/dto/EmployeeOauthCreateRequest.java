package erp.system.employeeoauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeOauthCreateRequest(
        @NotNull(message = "사원 ID는 필수입니다.") Long employeeId,
        @NotBlank(message = "제공자는 필수입니다.") @Size(max = 20) String provider,
        @NotBlank(message = "제공자 사용자 ID는 필수입니다.") @Size(max = 100) String providerUserId,
        @Size(max = 255) String providerEmail
) {
}
