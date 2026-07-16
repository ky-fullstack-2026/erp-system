package erp.system.employmenttype.service;


import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.employmenttype.dto.EmploymentTypeRequest;
import erp.system.employmenttype.dto.EmploymentTypeResponse;
import erp.system.employmenttype.entity.EmploymentType;
import erp.system.employmenttype.respository.EmploymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmploymentTypeService {

    private final EmploymentTypeRepository employmentTypeRepository;


    public List<EmploymentTypeResponse> findAll() {
        return employmentTypeRepository.findAll().stream().map(EmploymentTypeResponse::from).toList();
    }

    public EmploymentTypeResponse getById(Long employmentTypeId) {
        return EmploymentTypeResponse.from(findActive(employmentTypeId));
    }


    @Transactional
    public EmploymentTypeResponse create(EmploymentTypeRequest request) {
        EmploymentType employmentType = new EmploymentType(request.employmentTypeName());
        return EmploymentTypeResponse.from(employmentTypeRepository.save(employmentType));
    }

    @Transactional
    public EmploymentTypeResponse update(Long employmentTypeId, EmploymentTypeRequest request) {
        EmploymentType employmentType = findActive(employmentTypeId);
        employmentType.update(request.employmentTypeName());
        return EmploymentTypeResponse.from(employmentType);
    }

    @Transactional
    public void delete(Long employmentTypeId) {
        findActive(employmentTypeId).markDeleted();
    }
    private EmploymentType findActive(Long employmentTypeId) {
        return employmentTypeRepository.findById(employmentTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYMENT_TYPE_NOT_FOUND));
    }
}
