package erp.system.attendence.dto;

import java.util.List;

public record MonthlyAttendanceResponse (
        int year,
        int month,
        int totalWorkDays,
        int targetHeadcount,
        List<MonthlyAttendanceRowResponse> rows
){
}
