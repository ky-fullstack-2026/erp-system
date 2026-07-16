package erp.system.payroll.entity;

import erp.system.common.entity.BaseEntity;
import erp.system.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payroll")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payroll extends BaseEntity {
    public static final String STATUS_DRAFT = "DRAFT";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_PAID = "PAID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_id")
    private Long payrollId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "payroll_year_month", nullable = false, length = 6)
    private String payrollYearMonth;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "total_pay_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalPayAmount;

    @Column(name = "total_deduction_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalDeductionAmount;


    @Column(name = "real_pay_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal realPayAmount;

    @Column(name = "payroll_status_code", length = 30, nullable = false)
    private String payrollStatusCode;

    @Column(name = "employee_name_snapshot", length = 100)
    private String employeeNameSnapshot;

    @Column(name = "department_name_snapshot", length = 100)
    private String departmentNameSnapshot;

    @Column(name = "position_name_snapshot", length = 100)
    private String positionNameSnapshot;
    public Payroll(Employee employee, String payrollYearMonth, BigDecimal totalPayAmount, BigDecimal totalDeductionAmount) {
        this.employee = employee;
        this.payrollYearMonth = payrollYearMonth;
        this.totalPayAmount = totalPayAmount;
        this.totalDeductionAmount = totalDeductionAmount;
        this.realPayAmount = totalPayAmount.subtract(totalDeductionAmount);
        this.payrollStatusCode = STATUS_DRAFT;
        this.employeeNameSnapshot = employee.getName();
        this.departmentNameSnapshot = employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null;
        this.positionNameSnapshot = employee.getPosition() != null ? employee.getPosition().getPositionName() : null;
    }

    public void confirm(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        this.payrollStatusCode = STATUS_CONFIRMED;
    }

    public void markPaid() {
        this.payrollStatusCode = STATUS_PAID;
    }
}
