package erp.system.department.entity;

import erp.system.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@Entity
@Table(name = "department")
@SQLRestriction("deleted=false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    @Column(name = "parent_department_id")
    private Long parentDepartmentId;

    public Department(String departmentName, Long parentDepartmentId) {
        this.departmentName = departmentName;
        this.parentDepartmentId = parentDepartmentId;
    }

    public void update(String departmentName, Long parentDepartmentId) {
        this.departmentName = departmentName;
        this.parentDepartmentId = parentDepartmentId;
    }

}
