package erp.system.employeeleavebalance.repository;

import erp.system.employeeleavebalance.entity.EmployeeLeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance,Long> {
    List<EmployeeLeaveBalance> findByEmployee_EmployeeId(Long employeeId);

    Optional<EmployeeLeaveBalance> findByEmployee_EmployeeIdAndLeaveType_LeaveTypeId(Long employeeId, Long leaveTypeId);
}
