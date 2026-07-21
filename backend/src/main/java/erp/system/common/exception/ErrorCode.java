package erp.system.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "본인 데이터만 접근할 수 있습니다."),
    ACCOUNT_INACTIVE(HttpStatus.UNAUTHORIZED, "비활성화된 계정입니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "부서를 찾을 수 없습니다."),
    INVALID_PARENT_DEPARTMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 상위 부서입니다."),
    DEPARTMENT_IN_USE(HttpStatus.CONFLICT, "소속된 사원이 있는 부서는 삭제할 수 없습니다."),
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND, "직책을 찾을 수 없습니다."),
    EMPLOYMENT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "사원타입을 찾을 수 없습니다."),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "사원을 찾을 수 없습니다."),
    DUPLICATE_EMPLOYEE_NO(HttpStatus.CONFLICT, "이미 사용 중인 사번입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    APPOINTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "발령 이력을 찾을 수 없습니다."),
    EVENT_SUPPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "경조비 신청 내역을 찾을 수 없습니다."),
    CERTIFICATE_ISSUE_NOT_FOUND(HttpStatus.NOT_FOUND, "증명서 발급 내역을 찾을 수 없습니다."),
    LEAVE_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND,"휴가신청내역을 찾을 수 없습니다."),
    LEAVE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"휴가신청항목을 찾을 수 없습니다."),
    EMPLOYEE_OAUTH_NOT_FOUND(HttpStatus.NOT_FOUND, "연동된 소셜 계정을 찾을 수 없습니다."),
    DUPLICATE_OAUTH_ACCOUNT(HttpStatus.CONFLICT, "이미 다른 사원에 연동된 소셜 계정입니다."),
    DUPLICATE_OAUTH_PROVIDER(HttpStatus.CONFLICT, "이미 해당 제공자와 연동되어 있습니다."),
    EMPLOYEE_LEAVE_BALANCE_NOT_FOUND(HttpStatus.NOT_FOUND,"잔여휴가 내역을 찾을 수 없습니다."),
    PAYROLL_NOT_FOUND(HttpStatus.NOT_FOUND, "급여 내역을 찾을 수 없습니다."),
    INVALID_PAYROLL_STATUS(HttpStatus.BAD_REQUEST, "확정된 급여만 지급 처리할 수 있습니다."),
    PAYROLL_ITEM_MASTER_NOT_FOUND(HttpStatus.NOT_FOUND, "급여항목을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    PAYROLL_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "급여상세를 찾을 수 없습니다."),
    DUPLICATE_PAYROLL(HttpStatus.CONFLICT, "이미 해당 월의 급여 내역이 존재합니다."),
    INVALID_PAYROLL_ITEM_TYPE(HttpStatus.BAD_REQUEST, "항목 유형은 EARNING 또는 DEDUCTION만 가능합니다."),
    PAYROLL_ITEM_MASTER_IN_USE(HttpStatus.CONFLICT, "이미 급여 명세에서 사용 중인 항목은 삭제할 수 없습니다."),
    ;


    private final HttpStatus status;
    private final String defaultMessage;
    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
