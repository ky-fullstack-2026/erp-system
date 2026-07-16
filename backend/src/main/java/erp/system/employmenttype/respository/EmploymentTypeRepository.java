package erp.system.employmenttype.respository;

import erp.system.employmenttype.entity.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentTypeRepository extends JpaRepository<EmploymentType,Long> {
}
