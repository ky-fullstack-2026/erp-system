package erp.system.employee.dto;

import erp.system.employee.entity.Employee;

import java.time.LocalDate;

public record EmployeeSummaryResponse (
        Long employeeId,
        String employeeNo,
        String name,
        Long departmentId,
        String departmentName,
        Long positionId,
        String positionName,
        String email,
        String phone,
        LocalDate hireDate,
        String employeeStatusCode
){

    public static EmployeeSummaryResponse from(Employee employee) {
        return new EmployeeSummaryResponse(
                employee.getEmployeeId(),
                employee.getEmployeeNo(),
                employee.getName(),
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentId() : null,
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null,
                employee.getPosition() != null ? employee.getPosition().getPositionId() : null,
                employee.getPosition() != null ? employee.getPosition().getPositionName() : null,
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                employee.getEmployeeStatusCode()
        );
    }
}
