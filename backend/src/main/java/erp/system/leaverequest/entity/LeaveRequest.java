package erp.system.leaverequest.entity;

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
@Table(name = "leave_request")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LeaveRequest extends BaseEntity {

    public static final String STATUS_REQUESTED = "REQUESTED";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CANCELED = "CANCELED";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id")
    private Long leaveRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "leave_days", precision = 4, scale = 1)
    private BigDecimal leaveDays;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "approver_id")
    private Long approverId;

    public LeaveRequest(Employee employee, LeaveType leaveType, LocalDate startDate, LocalDate endDate,
                        BigDecimal leaveDays, String reason) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveDays = leaveDays;
        this.reason = reason;
        this.status = STATUS_REQUESTED;
    }
    public void approve(Long approverId) {
        this.approverId = approverId;
        this.status = STATUS_APPROVED;
    }

    public void reject(Long approverId) {
        this.approverId = approverId;
        this.status = STATUS_REJECTED;
    }

    public void cancel() {
        this.status = STATUS_CANCELED;
    }
}
