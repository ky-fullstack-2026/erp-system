package erp.system.attendence.repository;

import erp.system.attendence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployee_EmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    List<Attendance> findByWorkDateAndEmployee_EmployeeIdIn(LocalDate workDate, List<Long> employeeIds);

    List<Attendance> findByWorkDateBetweenAndEmployee_EmployeeIdIn(LocalDate startDate, LocalDate endDate, List<Long> employeeIds);


}
