package erp.system.payroll.service;


import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.employee.entity.Employee;
import erp.system.employee.repository.EmployeeRepository;
import erp.system.payroll.dto.PayrollConfirmRequest;
import erp.system.payroll.dto.PayrollCreateRequest;
import erp.system.payroll.dto.PayrollResponse;
import erp.system.payroll.dto.PayrollWithDetailsResponse;
import erp.system.payroll.entity.Payroll;
import erp.system.payroll.repository.PayrollRepository;
import erp.system.payrolldetail.dto.PayrollDetailResponse;
import erp.system.payrolldetail.repository.PayrollDetailRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayrollService {
    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollDetailRepository payrollDetailRepository;
    public List<PayrollResponse> getByEmployee(Long employeeId) {
        return payrollRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(PayrollResponse::from)
                .toList();
    }

    @Transactional
    public PayrollResponse create(PayrollCreateRequest request) {
        Employee employee = resolveEmployee(request.employeeId());

        if (payrollRepository.existsByEmployee_EmployeeIdAndPayrollYearMonth(request.employeeId(), request.payrollYearMonth())) {
            throw new BusinessException(ErrorCode.DUPLICATE_PAYROLL);
        }

        Payroll payroll = new Payroll(
                employee,
                request.payrollYearMonth(),
                request.totalPayAmount(),
                request.totalDeductionAmount()
        );

        return PayrollResponse.from(payrollRepository.save(payroll));
    }
    @Transactional
    public PayrollResponse confirm(Long payrollId, PayrollConfirmRequest request) {
        Payroll payroll = resolvePayroll(payrollId);
        payroll.confirm(request.paymentDate());
        return PayrollResponse.from(payroll);
    }

    @Transactional
    public PayrollResponse pay(Long payrollId) {
        Payroll payroll = resolvePayroll(payrollId);
        if (!Payroll.STATUS_CONFIRMED.equals(payroll.getPayrollStatusCode())) {
            throw new BusinessException(ErrorCode.INVALID_PAYROLL_STATUS);
        }
        payroll.markPaid();
        return PayrollResponse.from(payroll);
    }

    private Payroll resolvePayroll(Long payrollId) {
        return payrollRepository.findById(payrollId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYROLL_NOT_FOUND));
    }
    private Employee resolveEmployee(Long employeeId)  {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }

    public PayrollWithDetailsResponse getWithDetails(Long payrollId) {
        Payroll payroll = resolvePayroll(payrollId);
        List<PayrollDetailResponse> details = payrollDetailRepository.findByPayroll_PayrollId(payrollId).stream()
                .map(PayrollDetailResponse::from)
                .toList();
        return new PayrollWithDetailsResponse(PayrollResponse.from(payroll), details);
    }
}
