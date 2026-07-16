package erp.system.security;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Long currentEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public static void checkOwnerOrAdmin(Long targetEmployeeId) {
        if (isAdmin()) {
            return;
        }
        if (!currentEmployeeId().equals(targetEmployeeId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
