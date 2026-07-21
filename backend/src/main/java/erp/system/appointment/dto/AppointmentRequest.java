package erp.system.appointment.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AppointmentRequest(
        @NotNull(message = "사원은 필수입니다.") Long employeeId,
        String appointmentType,
        LocalDate appointmentDate,
        Long fromDepartmentId,
        Long toDepartmentId,
        String fromPositionName,
        String toPositionName,
        String reason,
        String memo
) {
}
