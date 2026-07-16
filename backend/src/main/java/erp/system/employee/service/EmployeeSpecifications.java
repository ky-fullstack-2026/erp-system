package erp.system.employee.service;

import erp.system.employee.entity.Employee;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {



    private EmployeeSpecifications() {
    }


    public static Specification<Employee> search(String keyword, Long departmentId, Long positionId, String status) {
        return (root, query, cb) -> {
            // 조건이 하나도 없을 때 true(전체 조회)가 되도록 빈 conjunction에서 시작
            var predicates = cb.conjunction();

            // 키워드: 이름 / 사번 / 이메일 중 하나라도 대소문자 무시하고 부분일치하면 매칭
            if (StringUtils.hasText(keyword)) {
                String like = "%" + keyword.trim().toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("employeeNo")), like),
                        cb.like(cb.lower(root.get("email")), like)
                ));
            }
            // 부서 필터: 값이 있을 때만 조건 추가 (null이면 전체 부서 대상)
            if (departmentId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("department").get("departmentId"), departmentId));
            }
            // 직책 필터: 값이 있을 때만 조건 추가 (null이면 전체 직책 대상)
            if (positionId != null) {
                predicates = cb.and(predicates, cb.equal(root.get("position").get("positionId"), positionId));
            }
            // 재직 상태 필터 (예: ACTIVE, RESIGNED 등 employeeStatusCode 값과 정확히 일치)
            if (StringUtils.hasText(status)) {
                predicates = cb.and(predicates, cb.equal(root.get("employeeStatusCode"), status));
            }
            return predicates;
        };
    }
}
