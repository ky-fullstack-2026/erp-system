package erp.system.attendence.dto;

import erp.system.attendence.entity.Attendance;
import erp.system.employee.entity.Employee;

import java.time.LocalDateTime;

public record AttendanceRosterResponse(
        Long employeeId,
        String employeeNo,
        String name,
        String departmentName,
        String positionName,
        Long attendanceId,
        String attendanceStatusCode,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String memo
) {

    public static AttendanceRosterResponse of(Employee employee, Attendance attendance) {
        return new AttendanceRosterResponse(
                employee.getEmployeeId(),
                employee.getEmployeeNo(),
                employee.getName(),
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null,
                employee.getPosition() != null ? employee.getPosition().getPositionName() : null,
                attendance != null ? attendance.getAttendanceId() : null,
                attendance != null ? attendance.getAttendanceStatusCode() : null,
                attendance != null ? attendance.getCheckInTime() : null,
                attendance != null ? attendance.getCheckOutTime() : null,
                attendance != null ? attendance.getMemo() : null
        );
    }
}
