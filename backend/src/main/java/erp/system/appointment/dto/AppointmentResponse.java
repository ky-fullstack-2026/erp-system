package erp.system.appointment.dto;

import erp.system.appointment.entity.EmployeeAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long employeeAppointmentId,
        String appointmentNo,
        Long employeeId,
        String employeeNo,
        String employeeName,
        String appointmentType,
        LocalDate appointmentDate,
        Long fromDepartmentId,
        String fromDepartmentName,
        Long toDepartmentId,
        String toDepartmentName,
        String fromPositionName,
        String toPositionName,
        String reason,
        String memo,
        String registeredBy,
        LocalDateTime createdAt
) {
    public static AppointmentResponse from(EmployeeAppointment appointment) {
        return new AppointmentResponse(
                appointment.getEmployeeAppointmentId(),
                appointment.getAppointmentNo(),
                appointment.getEmployee().getEmployeeId(),
                appointment.getEmployee().getEmployeeNo(),
                appointment.getEmployee().getName(),
                appointment.getAppointmentType(),
                appointment.getAppointmentDate(),
                appointment.getFromDepartment() != null ? appointment.getFromDepartment().getDepartmentId() : null,
                appointment.getFromDepartment() != null ? appointment.getFromDepartment().getDepartmentName() : null,
                appointment.getToDepartment() != null ? appointment.getToDepartment().getDepartmentId() : null,
                appointment.getToDepartment() != null ? appointment.getToDepartment().getDepartmentName() : null,
                appointment.getFromPositionName(),
                appointment.getToPositionName(),
                appointment.getReason(),
                appointment.getMemo(),
                appointment.getRegisteredBy(),
                appointment.getCreatedAt()
        );
    }
}
