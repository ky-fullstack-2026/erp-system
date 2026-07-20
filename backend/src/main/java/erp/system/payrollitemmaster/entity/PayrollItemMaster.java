package erp.system.payrollitemmaster.entity;

import erp.system.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "payroll_item_master")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PayrollItemMaster extends BaseEntity {

    public static final String TYPE_EARNING = "EARNING";
    public static final String TYPE_DEDUCTION = "DEDUCTION";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payroll_item_master_id")
    private Long payrollItemMasterId;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(name = "item_type_code", nullable = false, length = 30)
    private String itemTypeCode;

    @Column(name = "taxable", nullable = false)
    private boolean taxable;

    @Column(name = "fixed", nullable = false)
    private boolean fixed;
    @Builder
    public PayrollItemMaster(String itemName, String itemTypeCode, boolean taxable, boolean fixed) {
        this.itemName = itemName;
        this.itemTypeCode = itemTypeCode;
        this.taxable = taxable;
        this.fixed = fixed;
    }

    public void update(String itemName, String itemTypeCode, boolean taxable, boolean fixed) {
        this.itemName = itemName;
        this.itemTypeCode = itemTypeCode;
        this.taxable = taxable;
        this.fixed = fixed;
    }


}
