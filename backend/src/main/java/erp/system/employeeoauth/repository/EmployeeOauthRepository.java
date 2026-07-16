package erp.system.employeeoauth.repository;

import erp.system.employeeoauth.entity.EmployeeOauth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeOauthRepository extends JpaRepository<EmployeeOauth,Long> {
    List<EmployeeOauth> findByEmployee_EmployeeId(Long employeeId);

    Optional<EmployeeOauth> findByProviderAndProviderUserId(String provider, String providerUserId);

    Optional<EmployeeOauth> findByEmployee_EmployeeIdAndProvider(Long employeeId, String provider);

    boolean existsByProviderAndProviderUserId(String provider, String providerUserId);
}
