package erp.system.attendence.dto;

public record AttendanceCounts(
        long total,
        long checkIn,
        long late,
        long absent,
        long annualLeave
) {
}
