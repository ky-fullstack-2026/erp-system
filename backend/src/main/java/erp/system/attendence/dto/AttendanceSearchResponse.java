package erp.system.attendence.dto;

import java.util.List;

public record AttendanceSearchResponse(
        List<AttendanceRosterResponse> content,
        AttendanceCounts counts,
        long totalElements,
        int totalPages,
        int number,
        int size
) {
}
