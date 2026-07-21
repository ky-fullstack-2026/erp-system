package erp.system.appointment.service;

import erp.system.appointment.entity.EmployeeAppointment;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AppointmentSpecifications {

    private AppointmentSpecifications() {
    }

    public static Specification<EmployeeAppointment> search(String keyword, String appointmentType,
                                                              LocalDate fromDate, LocalDate toDate) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (StringUtils.hasText(keyword)) {
                String like = "%" + keyword.trim().toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("employee").get("name")), like),
                        cb.like(cb.lower(root.get("employee").get("employeeNo")), like)
                ));
            }
            if (StringUtils.hasText(appointmentType)) {
                predicates = cb.and(predicates, cb.equal(root.get("appointmentType"), appointmentType));
            }
            if (fromDate != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("appointmentDate"), fromDate));
            }
            if (toDate != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("appointmentDate"), toDate));
            }
            return predicates;
        };
    }
}
