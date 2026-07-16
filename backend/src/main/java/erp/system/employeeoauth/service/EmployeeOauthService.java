package erp.system.employeeoauth.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.employeeoauth.dto.EmployeeOauthCreateRequest;
import erp.system.employeeoauth.dto.EmployeeOauthResponse;
import erp.system.employeeoauth.entity.EmployeeOauth;
import erp.system.employeeoauth.repository.EmployeeOauthRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeOauthService {

    private final EmployeeOauthRepository employeeOauthRepository;
    private final EmployeeRepository employeeRepository;

    public List<EmployeeOauthResponse> getByEmployee(Long employeeId) {
        return employeeOauthRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(EmployeeOauthResponse::from)
                .toList();
    }


    @Transactional
    public EmployeeOauthResponse link(EmployeeOauthCreateRequest request) {
        Employee employee = resolveEmployee(request.employeeId());

        if (employeeOauthRepository.existsByProviderAndProviderUserId(request.provider(), request.providerUserId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_OAUTH_ACCOUNT);
        }
        if (employeeOauthRepository.findByEmployee_EmployeeIdAndProvider(request.employeeId(), request.provider()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_OAUTH_PROVIDER);
        }

        EmployeeOauth oauth = new EmployeeOauth(
                employee,
                request.provider(),
                request.providerUserId(),
                request.providerEmail()
        );

        return EmployeeOauthResponse.from(employeeOauthRepository.save(oauth));
    }
    @Transactional
    public void unlink(Long employeeOauthId, Long employeeId) {
        EmployeeOauth oauth = employeeOauthRepository.findById(employeeOauthId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_OAUTH_NOT_FOUND));

        if (!oauth.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new BusinessException(ErrorCode.EMPLOYEE_OAUTH_NOT_FOUND);
        }

        employeeOauthRepository.delete(oauth);
    }

    private Employee resolveEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(()->new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }


}
