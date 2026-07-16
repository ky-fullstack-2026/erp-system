package erp.system.leavetype.entity;


import erp.system.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "leave_type")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeaveType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_type_id")
    private Long leaveTypeId;

    @Column(name = "leave_type_name", nullable = false, length = 100)
    private String leaveTypeName;

    @Column(name = "paid_yn", nullable = false)
    private boolean paidYn;

    @Column(name = "default_days", precision = 4, scale = 1)
    private BigDecimal defaultDays;

    @Column(name = "note", length = 255)
    private String note;

    public LeaveType(String leaveTypeName, boolean paidYn, BigDecimal defaultDays, String note) {
        this.leaveTypeName = leaveTypeName;
        this.paidYn = paidYn;
        this.defaultDays = defaultDays;
        this.note = note;
    }

    public void update(String leaveTypeName, boolean paidYn, BigDecimal defaultDays, String note) {
        this.leaveTypeName = leaveTypeName;
        this.paidYn = paidYn;
        this.defaultDays = defaultDays;
        this.note = note;
    }
}
