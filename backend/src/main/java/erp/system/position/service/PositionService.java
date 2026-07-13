package erp.system.position.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.position.dto.PositionRequest;
import erp.system.position.dto.PositionResponse;
import erp.system.position.entity.Position;
import erp.system.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionService {
    private final PositionRepository positionRepository;

    public List<PositionResponse> findAll() {
        return positionRepository.findAll().stream()
                .map(PositionResponse::from)
                .toList();
    }

    public PositionResponse getById(Long positionId) {
        return PositionResponse.from(findActive(positionId));
    }
    @Transactional
    public PositionResponse create(PositionRequest request) {
        Position position = new Position(request.positionName(), request.level());
        return PositionResponse.from(positionRepository.save(position));
    }
    @Transactional
    public PositionResponse update(Long positionId, PositionRequest request) {
        Position position = findActive(positionId);
        position.update(request.positionName(), request.level());
        return PositionResponse.from(position);
    }

    @Transactional
    public void delete(Long positionId) {
        findActive(positionId).markDeleted();
    }
    private Position findActive(Long positionId){
        return positionRepository.findById(positionId)
                .orElseThrow(()->new BusinessException(ErrorCode.POSITION_NOT_FOUND));
    }
}
