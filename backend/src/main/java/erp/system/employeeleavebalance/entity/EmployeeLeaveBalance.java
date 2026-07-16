package erp.system.employeeleavebalance.entity;


import erp.system.common.entity.BaseEntity;
import erp.system.employee.entity.Employee;
import erp.system.leavetype.entity.LeaveType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee_leave_balance")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmployeeLeaveBalance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_leave_balance_id")
    private Long employeeLeaveBalanceId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "total_days", precision = 5, scale = 1, nullable = false)
    private BigDecimal totalDays;

    @Column(name = "used_days", precision = 5, scale = 1, nullable = false)
    private BigDecimal usedDays;

    @Column(name = "remain_days", precision = 5, scale = 1, nullable = false)
    private BigDecimal remainDays;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    public EmployeeLeaveBalance(Employee employee, LeaveType leaveType, BigDecimal totalDays, LocalDate expireDate) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.totalDays = totalDays;
        this.usedDays = BigDecimal.ZERO;
        this.remainDays = totalDays;
        this.expireDate = expireDate;
    }

    public void use(BigDecimal days) {
        this.usedDays = this.usedDays.add(days);
        this.remainDays = this.remainDays.subtract(days);
    }
}
