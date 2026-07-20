package erp.system.employee.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.department.entity.Department;
import erp.system.department.repository.DepartmentRepository;
import erp.system.employee.dto.EmployeeCreateRequest;
import erp.system.employee.dto.EmployeeResponse;
import erp.system.employee.dto.EmployeeSummaryResponse;
import erp.system.employee.dto.EmployeeUpdateRequest;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.employmenttype.entity.EmploymentType;
import erp.system.employmenttype.respository.EmploymentTypeRepository;
import erp.system.position.entity.Position;
import erp.system.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmploymentTypeRepository employmentTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeResponse getById(Long employeeId){
        return EmployeeResponse.from(findActive(employeeId));
    }
    public Page<EmployeeSummaryResponse> search(String keyword, Long departmentId, Long positionId, String status, Pageable pageable) {
        var spec = EmployeeSpecifications.search(keyword, departmentId, positionId, status);
        return employeeRepository.findAll(spec, pageable)
                .map(EmployeeSummaryResponse::from);
    }

    @Transactional
    public EmployeeResponse create(EmployeeCreateRequest request){
        if(employeeRepository.existsByEmployeeNo(request.employeeNo())){
            throw new BusinessException(ErrorCode.DUPLICATE_EMPLOYEE_NO);
        }
        if (request.email() != null && employeeRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
        Employee employee = Employee.builder()
                .role(Employee.ROLE_EMPLOYEE)
                .employeeNo(request.employeeNo())
                .department(resoloveDepartment(request.departmentId()))
                .position(resolvePosition(request.positionId()))
                .employmentType(resolveEmploymentType(request.employmentTypeId()))
                .name(request.name())
                .birthDate(request.birthDate())
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .hireDate(request.hireDate())
                .employeeStatusCode(request.employeeStatusCode())
                .bankName(request.bankName())
                .accountNumber(request.accountNumber())
                .accountHolder(request.accountHolder())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return EmployeeResponse.from(employeeRepository.save(employee));
    }


    @Transactional
    public EmployeeResponse update(Long employeeId, EmployeeUpdateRequest request){
        Employee employee = findActive(employeeId);
        if (request.email() != null && !request.email().equals(employee.getEmail())
                && employeeRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }
        employee.update(
                resoloveDepartment(request.departmentId()),
                resolvePosition(request.positionId()),
                resolveEmploymentType(request.employmentTypeId()),
                request.name(),
                request.birthDate(),
                request.phone(),
                request.email(),
                request.address(),
                request.hireDate(),
                request.resignationDate(),
                request.employeeStatusCode(),
                request.bankName(),
                request.accountNumber(),
                request.accountHolder()
        );

        return EmployeeResponse.from(employee);
    }

    @Transactional
    public void delete(Long employeeId) {
        Employee employee = findActive(employeeId);
        employee.markDeleted();
    }

    private Employee findActive(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(()->new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));

    }

    private Position resolvePosition(Long positionId){
        if (positionId == null) {
            return null;
        }
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POSITION_NOT_FOUND));
    }
    private EmploymentType resolveEmploymentType(Long employmentTypeId){
        if (employmentTypeId == null) {
            return null;
        }
        return employmentTypeRepository.findById(employmentTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYMENT_TYPE_NOT_FOUND));
    }
    private Department resoloveDepartment(Long departmentId){
        if(departmentId==null){
            return  null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }
}
