package erp.system.department.dto;

import erp.system.department.entity.Department;

import java.time.LocalDateTime;

public record DepartmentResponse(
        Long departmentId,
        String departmentName,
        Long parentDepartmentId
) {
    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getParentDepartmentId()
        );
    }
}
