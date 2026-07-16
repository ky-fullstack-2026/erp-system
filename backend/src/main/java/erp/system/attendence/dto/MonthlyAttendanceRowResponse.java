package erp.system.attendence.dto;

import java.util.Map;

public record MonthlyAttendanceRowResponse(
        Long employeeId,
        String employeeNo,
        String name,
        String departmentName,
        Map<Integer, String> days,
        long checkIn,
        long late,
        long annualLeave,
        long absent
) {
}
