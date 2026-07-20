package erp.system.payroll.dto;

import erp.system.payrolldetail.dto.PayrollDetailResponse;

import java.util.List;

public record PayrollWithDetailsResponse(
        PayrollResponse payroll,
        List<PayrollDetailResponse> details
) {
}
