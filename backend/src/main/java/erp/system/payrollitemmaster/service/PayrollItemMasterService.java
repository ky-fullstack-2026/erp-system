package erp.system.payrollitemmaster.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.payrolldetail.repository.PayrollDetailRepository;
import erp.system.payrollitemmaster.dto.PayrollItemMasterRequest;
import erp.system.payrollitemmaster.dto.PayrollItemMasterResponse;
import erp.system.payrollitemmaster.entity.PayrollItemMaster;
import erp.system.payrollitemmaster.repository.PayrollItemMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PayrollItemMasterService {
    private final PayrollItemMasterRepository repository;
    private final PayrollDetailRepository payrollDetailRepository;
    public List<PayrollItemMasterResponse> getAll() {
        return repository.findAll().stream().map(PayrollItemMasterResponse::from).toList();
    }
    @Transactional
    public PayrollItemMasterResponse create(PayrollItemMasterRequest request) {
        validateItemTypeCode(request.itemTypeCode());
        PayrollItemMaster m = PayrollItemMaster.builder()
                .itemName(request.itemName())
                .itemTypeCode(request.itemTypeCode())
                .taxable(request.taxable())
                .fixed(request.fixed())
                .build();
        return PayrollItemMasterResponse.from(repository.save(m));
    }
    @Transactional
    public PayrollItemMasterResponse update(Long id, PayrollItemMasterRequest request) {
        validateItemTypeCode(request.itemTypeCode());   // ← 추가
        PayrollItemMaster m = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYROLL_ITEM_MASTER_NOT_FOUND));
        m.update(request.itemName(), request.itemTypeCode(), request.taxable(), request.fixed());
        return PayrollItemMasterResponse.from(m);
    }

    @Transactional
    public void delete(Long id) {
        PayrollItemMaster m = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYROLL_ITEM_MASTER_NOT_FOUND));
        if (payrollDetailRepository.existsByPayrollItemMaster_PayrollItemMasterId(id)) {   // ← 추가
            throw new BusinessException(ErrorCode.PAYROLL_ITEM_MASTER_IN_USE);              // ← 추가
        }
        m.markDeleted();
    }

    private void validateItemTypeCode(String itemTypeCode) {
        if (!PayrollItemMaster.TYPE_EARNING.equals(itemTypeCode)
                && !PayrollItemMaster.TYPE_DEDUCTION.equals(itemTypeCode)) {
            throw new BusinessException(ErrorCode.INVALID_PAYROLL_ITEM_TYPE);
        }
    }
}
