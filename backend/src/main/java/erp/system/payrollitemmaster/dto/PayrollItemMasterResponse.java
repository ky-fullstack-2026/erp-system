package erp.system.payrollitemmaster.dto;

import erp.system.payrollitemmaster.entity.PayrollItemMaster;

public record PayrollItemMasterResponse(
        Long payrollItemMasterId,
        String itemName,
        String itemTypeCode,
        boolean taxable,
        boolean fixed,
        boolean active
) {

    public static PayrollItemMasterResponse from(PayrollItemMaster m) {
        return new PayrollItemMasterResponse(
                m.getPayrollItemMasterId(), m.getItemName(), m.getItemTypeCode(),
                m.isTaxable(), m.isFixed(), m.isActive()
        );
    }
}
