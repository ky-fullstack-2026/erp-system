package erp.system.employmenttype.dto;

import erp.system.employmenttype.entity.EmploymentType;

public record EmploymentTypeResponse(
        Long employmentTypeId,
        String employmentTypeName
) {
    public static EmploymentTypeResponse from(EmploymentType employmentType) {
        return new EmploymentTypeResponse(employmentType.getEmploymentTypeId(), employmentType.getEmploymentTypeName());
    }
}
