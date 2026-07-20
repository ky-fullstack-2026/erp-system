package erp.system.payrolldetail.entity;

import erp.system.common.entity.DeleteTableEntity;
import erp.system.payroll.entity.Payroll;
import erp.system.payrollitemmaster.entity.PayrollItemMaster;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "payroll_detail")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class PayrollDetail extends DeleteTableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_detail_id")
    private Long payrollDetailId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id", nullable = false)
    private Payroll payroll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_item_master_id", nullable = false)
    private PayrollItemMaster payrollItemMaster;

    @Column(name = "item_name_snapshot", length = 100)
    private String itemNameSnapshot;

    @Column(name = "item_type_code", length = 30)
    private String itemTypeCode;

    @Column(name = "amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    public PayrollDetail(Payroll payroll, PayrollItemMaster payrollItemMaster, BigDecimal amount) {
        this.payroll = payroll;
        this.payrollItemMaster = payrollItemMaster;
        this.itemNameSnapshot = payrollItemMaster.getItemName();
        this.itemTypeCode = payrollItemMaster.getItemTypeCode();
        this.amount = amount;
    }
}
