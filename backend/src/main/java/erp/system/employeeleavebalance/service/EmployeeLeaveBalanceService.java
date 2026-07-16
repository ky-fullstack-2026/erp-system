package erp.system.employeeleavebalance.service;


import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.employeeleavebalance.dto.EmployeeLeaveBalanceCreateRequest;
import erp.system.employeeleavebalance.dto.EmployeeLeaveBalanceResponse;
import erp.system.employeeleavebalance.entity.EmployeeLeaveBalance;
import erp.system.employeeleavebalance.repository.EmployeeLeaveBalanceRepository;
import erp.system.leavetype.entity.LeaveType;
import erp.system.leavetype.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeLeaveBalanceService {
    private final EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;


    public List<EmployeeLeaveBalanceResponse> getByEmployee(Long employeeId) {
        return employeeLeaveBalanceRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(EmployeeLeaveBalanceResponse::from)
                .toList();
    }

    @Transactional
    public EmployeeLeaveBalanceResponse create(EmployeeLeaveBalanceCreateRequest request) {
        Employee employee = resolveEmployee(request.employeeId());
        LeaveType leaveType = resolveLeaveType(request.leaveTypeId());

        EmployeeLeaveBalance balance = new EmployeeLeaveBalance(
                employee,
                leaveType,
                request.totalDays(),
                request.expireDate()
        );

        return EmployeeLeaveBalanceResponse.from(employeeLeaveBalanceRepository.save(balance));
    }

    @Transactional
    public void use(Long employeeId, Long leaveTypeId, BigDecimal days) {
        EmployeeLeaveBalance balance = employeeLeaveBalanceRepository
                .findByEmployee_EmployeeIdAndLeaveType_LeaveTypeId(employeeId, leaveTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_LEAVE_BALANCE_NOT_FOUND));
        balance.use(days);
    }


    private Employee resolveEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    private LeaveType resolveLeaveType(Long leaveTypeId) {
        return leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LEAVE_TYPE_NOT_FOUND));
    }
}
