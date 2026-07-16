package erp.system.payroll.repository;

import erp.system.payroll.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    List<Payroll> findByEmployee_EmployeeId(Long employeeId);

    List<Payroll> findByPayrollYearMonth(String payrollYearMonth);

    Optional<Payroll> findByEmployee_EmployeeIdAndPayrollYearMonth(Long employeeId, String payrollYearMonth);

    boolean existsByEmployee_EmployeeIdAndPayrollYearMonth(Long employeeId, String payrollYearMonth);
}
