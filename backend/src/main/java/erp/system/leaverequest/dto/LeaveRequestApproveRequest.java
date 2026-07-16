package erp.system.leaverequest.dto;

import jakarta.validation.constraints.NotNull;

public record LeaveRequestApproveRequest(
        @NotNull(message = "승인자 ID는 필수입니다.") Long approverId
) {
}
