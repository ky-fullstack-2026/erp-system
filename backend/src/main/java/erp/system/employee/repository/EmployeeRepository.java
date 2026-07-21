package erp.system.employee.repository;

import erp.system.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByEmployeeNoOrEmail(String employeeNo, String email);

    boolean existsByEmployeeNo(String employeeNo);

    boolean existsByEmail(String email);

    boolean existsByDepartment_DepartmentId(Long departmentId);

    // @SQLRestriction("deleted=false")로 소프트 삭제된 행은 제외되므로, 사번 유일성 체크(unique 컬럼 충돌 방지)는
    // 삭제 여부와 무관하게 실제 테이블 전체를 봐야 해서 네이티브 쿼리로 우회한다.
    @Query(value = "SELECT COUNT(*) FROM employee WHERE employee_no = :employeeNo", nativeQuery = true)
    long countByEmployeeNoIncludingDeleted(@Param("employeeNo") String employeeNo);
}
