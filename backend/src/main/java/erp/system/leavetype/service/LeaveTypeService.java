package erp.system.leavetype.service;

import erp.system.leavetype.dto.LeaveTypeCreateRequest;
import erp.system.leavetype.dto.LeaveTypeResponse;
import erp.system.leavetype.entity.LeaveType;
import erp.system.leavetype.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;

    public List<LeaveTypeResponse> getAll() {
        return leaveTypeRepository.findAll().stream()
                .map(LeaveTypeResponse::from)
                .toList();
    }
    @Transactional
    public LeaveTypeResponse create(LeaveTypeCreateRequest request) {
        LeaveType leaveType = new LeaveType(
                request.leaveTypeName(),
                request.paidYn(),
                request.defaultDays(),
                request.note()
        );
        return LeaveTypeResponse.from(leaveTypeRepository.save(leaveType));
    }
}
