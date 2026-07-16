package erp.system.leavetype.repository;

import erp.system.leavetype.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {
}
