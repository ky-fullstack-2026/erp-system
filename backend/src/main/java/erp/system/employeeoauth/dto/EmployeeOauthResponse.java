package erp.system.employeeoauth.dto;

import erp.system.employeeoauth.entity.EmployeeOauth;

import java.time.LocalDateTime;

public record EmployeeOauthResponse (
        Long employeeOauthId,
        Long employeeId,
        String employeeName,
        String provider,
        String providerUserId,
        String providerEmail,
        LocalDateTime createdAt
){
    public static EmployeeOauthResponse from(EmployeeOauth oauth) {
        return new EmployeeOauthResponse(
                oauth.getEmployeeOauthId(),
                oauth.getEmployee().getEmployeeId(),
                oauth.getEmployee().getName(),
                oauth.getProvider(),
                oauth.getProviderUserId(),
                oauth.getProviderEmail(),
                oauth.getCreatedAt()
        );
    }
}
