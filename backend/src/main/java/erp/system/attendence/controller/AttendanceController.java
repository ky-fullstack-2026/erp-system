package erp.system.attendence.controller;

import erp.system.attendence.dto.*;
import erp.system.attendence.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {


    private final AttendanceService attendanceService;

    @GetMapping
    public AttendanceSearchResponse search(
            @RequestParam LocalDate workDate,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        return attendanceService.search(workDate, departmentId, keyword, pageable);
    }
    @PostMapping
    public AttendanceRosterResponse upsert(@Valid @RequestBody AttendanceUpsertRequest request) {
        return attendanceService.upsert(request);
    }

    @PostMapping("/bulk")
    public void bulkUpsert(@Valid @RequestBody AttendanceBulkRequest request) {
        attendanceService.bulkUpsert(request);
    }

    @GetMapping("/monthly")
    public MonthlyAttendanceResponse monthly(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Long departmentId) {
        return attendanceService.getMonthly(YearMonth.of(year, month), departmentId);
    }

}
