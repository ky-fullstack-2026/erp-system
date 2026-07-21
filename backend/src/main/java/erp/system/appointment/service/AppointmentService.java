package erp.system.appointment.service;

import erp.system.appointment.dto.AppointmentRequest;
import erp.system.appointment.dto.AppointmentResponse;
import erp.system.appointment.entity.EmployeeAppointment;
import erp.system.appointment.repository.EmployeeAppointmentRepository;
import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.department.entity.Department;
import erp.system.department.repository.DepartmentRepository;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    private static final String APPOINTMENT_NO_PREFIX = "AP";
    private static final int APPOINTMENT_NO_DIGITS = 6;

    private final EmployeeAppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public Page<AppointmentResponse> search(String keyword, String appointmentType, LocalDate fromDate,
                                             LocalDate toDate, Pageable pageable) {
        var spec = AppointmentSpecifications.search(keyword, appointmentType, fromDate, toDate);
        return appointmentRepository.findAll(spec, pageable).map(AppointmentResponse::from);
    }

    public AppointmentResponse getById(Long employeeAppointmentId) {
        return AppointmentResponse.from(findActive(employeeAppointmentId));
    }

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));

        EmployeeAppointment appointment = EmployeeAppointment.builder()
                .appointmentNo(generateAppointmentNo())
                .employee(employee)
                .appointmentType(request.appointmentType())
                .appointmentDate(request.appointmentDate())
                .fromDepartment(resolveDepartment(request.fromDepartmentId()))
                .toDepartment(resolveDepartment(request.toDepartmentId()))
                .fromPositionName(request.fromPositionName())
                .toPositionName(request.toPositionName())
                .reason(request.reason())
                .memo(request.memo())
                .registeredBy(currentEmployeeName())
                .build();

        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse update(Long employeeAppointmentId, AppointmentRequest request) {
        EmployeeAppointment appointment = findActive(employeeAppointmentId);
        appointment.update(
                request.appointmentType(),
                request.appointmentDate(),
                resolveDepartment(request.fromDepartmentId()),
                resolveDepartment(request.toDepartmentId()),
                request.fromPositionName(),
                request.toPositionName(),
                request.reason(),
                request.memo()
        );
        return AppointmentResponse.from(appointment);
    }

    @Transactional
    public void delete(Long employeeAppointmentId) {
        findActive(employeeAppointmentId).markDeleted();
    }

    private EmployeeAppointment findActive(Long employeeAppointmentId) {
        return appointmentRepository.findById(employeeAppointmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPOINTMENT_NOT_FOUND));
    }

    private Department resolveDepartment(Long departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    private String currentEmployeeName() {
        return employeeRepository.findById(SecurityUtils.currentEmployeeId())
                .map(Employee::getName)
                .orElse(null);
    }

    private String generateAppointmentNo() {
        long seq = appointmentRepository.count() + 1;
        String candidate;
        do {
            candidate = String.format("%s%0" + APPOINTMENT_NO_DIGITS + "d", APPOINTMENT_NO_PREFIX, seq++);
        } while (appointmentRepository.countByAppointmentNoIncludingDeleted(candidate) > 0);
        return candidate;
    }
}
