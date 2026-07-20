package erp.system.payrolldetail.dto;

import erp.system.payrolldetail.entity.PayrollDetail;

import java.math.BigDecimal;

public record PayrollDetailResponse(
        Long payrollDetailId, Long payrollId, Long payrollItemMasterId,
        String itemNameSnapshot, String itemTypeCode, BigDecimal amount
) {

    public static PayrollDetailResponse from(PayrollDetail d) {
        return new PayrollDetailResponse(
                d.getPayrollDetailId(), d.getPayroll().getPayrollId(),
                d.getPayrollItemMaster().getPayrollItemMasterId(),
                d.getItemNameSnapshot(), d.getItemTypeCode(), d.getAmount()
        );
    }
}
