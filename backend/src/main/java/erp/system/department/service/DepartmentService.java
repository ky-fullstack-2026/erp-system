package erp.system.department.service;

import erp.system.common.exception.BusinessException;
import erp.system.common.exception.ErrorCode;
import erp.system.department.dto.DepartmentRequest;
import erp.system.department.dto.DepartmentResponse;
import erp.system.department.dto.DepartmentTreeNode;
import erp.system.department.entity.Department;
import erp.system.department.repository.DepartmentRepository;
import erp.system.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;




    public List<DepartmentResponse> findAll(){
        return departmentRepository.findAll().stream()
                .map(DepartmentResponse::from)
                .toList();
    }
    public DepartmentResponse getById(Long departmentId){
        return DepartmentResponse.from(findActive(departmentId));
    }
    public List<DepartmentTreeNode> getTree(){
        List<Department> departments = departmentRepository.findAll();

        Map<Long,DepartmentTreeNode> nodesById=new HashMap<>();
        for (Department department : departments) {
            nodesById.put(department.getDepartmentId(),
                    DepartmentTreeNode.of(department.getDepartmentId(), department.getDepartmentName()));
        }


        List<DepartmentTreeNode> roots = new java.util.ArrayList<>();
        for (Department department : departments) {
            DepartmentTreeNode node = nodesById.get(department.getDepartmentId());
            Long parentId = department.getParentDepartmentId();
            if (parentId != null && nodesById.containsKey(parentId)) {
                nodesById.get(parentId).children().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }


    @Transactional
    public DepartmentResponse create(DepartmentRequest request){
        validateParent(request.parentDepartmentId(), null);
        Department department =new Department(request.departmentName(),request.parentDepartmentId());
        return  DepartmentResponse.from(departmentRepository.save(department));
    }


    @Transactional
    public DepartmentResponse update(Long departmentId, DepartmentRequest request) {
        Department department = findActive(departmentId);
        validateParent(request.parentDepartmentId(), departmentId);
        department.update(request.departmentName(), request.parentDepartmentId());
        return DepartmentResponse.from(department);
    }

    @Transactional
    public void delete(Long departmentId) {
        Department department = findActive(departmentId);
        if (employeeRepository.existsByDepartment_DepartmentId(departmentId)) {
            throw new BusinessException(ErrorCode.DEPARTMENT_IN_USE);
        }
        department.markDeleted();
    }

    private Department findActive(Long departmentId){
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    // parentDepartmentId가 실제 존재하는 부서인지, 자기 자신이거나 자신의 하위 부서를 상위로 지정해
    // 순환 참조가 생기지는 않는지 검증한다.
    private void validateParent(Long parentDepartmentId, Long selfId) {
        if (parentDepartmentId == null) {
            return;
        }
        if (parentDepartmentId.equals(selfId)) {
            throw new BusinessException(ErrorCode.INVALID_PARENT_DEPARTMENT);
        }
        Department parent = departmentRepository.findById(parentDepartmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));

        if (selfId != null) {
            Long current = parent.getParentDepartmentId();
            while (current != null) {
                if (current.equals(selfId)) {
                    throw new BusinessException(ErrorCode.INVALID_PARENT_DEPARTMENT);
                }
                current = departmentRepository.findById(current)
                        .map(Department::getParentDepartmentId)
                        .orElse(null);
            }
        }
    }
}
