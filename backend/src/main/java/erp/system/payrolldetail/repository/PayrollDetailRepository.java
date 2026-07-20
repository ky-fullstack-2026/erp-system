package erp.system.payrolldetail.repository;

import erp.system.payrolldetail.entity.PayrollDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollDetailRepository extends JpaRepository<PayrollDetail, Long> {


    List<PayrollDetail> findByPayroll_PayrollId(Long payrollId);
    boolean existsByPayrollItemMaster_PayrollItemMasterId(Long payrollItemMasterId);
}
