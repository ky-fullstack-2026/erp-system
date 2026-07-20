package erp.system.payrolldetail.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.payroll.entity.Payroll;
import erp.system.payroll.repository.PayrollRepository;
import erp.system.payrolldetail.dto.PayrollDetailCreateRequest;
import erp.system.payrolldetail.dto.PayrollDetailResponse;
import erp.system.payrolldetail.entity.PayrollDetail;
import erp.system.payrolldetail.repository.PayrollDetailRepository;
import erp.system.payrollitemmaster.entity.PayrollItemMaster;
import erp.system.payrollitemmaster.repository.PayrollItemMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayrollDetailService {
    private final PayrollDetailRepository payrollDetailRepository;
    private final PayrollRepository payrollRepository;
    private final PayrollItemMasterRepository payrollItemMasterRepository;

    public List<PayrollDetailResponse> getByPayroll(Long payrollId) {
        return payrollDetailRepository.findByPayroll_PayrollId(payrollId).stream()
                .map(PayrollDetailResponse::from)
                .toList();
    }

    @Transactional
    public PayrollDetailResponse create(PayrollDetailCreateRequest request) {
        Payroll payroll = payrollRepository.findById(request.payrollId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYROLL_NOT_FOUND));
        PayrollItemMaster item = payrollItemMasterRepository.findById(request.payrollItemMasterId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYROLL_ITEM_MASTER_NOT_FOUND));

        PayrollDetail detail = new PayrollDetail(payroll, item, request.amount());
        return PayrollDetailResponse.from(payrollDetailRepository.save(detail));
    }


}
