package erp.system.appointment.repository;

import erp.system.appointment.entity.EmployeeAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeAppointmentRepository
        extends JpaRepository<EmployeeAppointment, Long>, JpaSpecificationExecutor<EmployeeAppointment> {

    // @SQLRestriction("deleted=false")로 소프트 삭제된 행은 제외되므로, 발령번호 유일성 체크(unique 컬럼 충돌 방지)는
    // 삭제 여부와 무관하게 실제 테이블 전체를 봐야 해서 네이티브 쿼리로 우회한다.
    @Query(value = "SELECT COUNT(*) FROM employee_appointment WHERE appointment_no = :appointmentNo", nativeQuery = true)
    long countByAppointmentNoIncludingDeleted(@Param("appointmentNo") String appointmentNo);
}
