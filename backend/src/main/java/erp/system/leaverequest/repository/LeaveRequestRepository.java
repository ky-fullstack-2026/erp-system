package erp.system.leaverequest.repository;

import erp.system.leaverequest.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    List<LeaveRequest> findByEmployee_EmployeeId(Long employeeId);
}
