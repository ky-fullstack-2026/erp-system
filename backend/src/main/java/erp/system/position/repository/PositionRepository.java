package erp.system.position.repository;

import erp.system.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {
    boolean existsByPositionName(String positionName);
}
