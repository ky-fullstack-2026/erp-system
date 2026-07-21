package erp.system.config;

import erp.system.employmenttype.entity.EmploymentType;
import erp.system.employmenttype.respository.EmploymentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmploymentTypeInitializer implements CommandLineRunner {

    private final EmploymentTypeRepository employmentTypeRepository;

    private static final List<String> DEFAULT_EMPLOYMENT_TYPES = List.of(
            "정규직", "계약직", "인턴", "파견직", "시간제"
    );

    @Override
    public void run(String... args) {
        if (employmentTypeRepository.count() > 0) {
            return;
        }
        DEFAULT_EMPLOYMENT_TYPES.forEach(name -> employmentTypeRepository.save(new EmploymentType(name)));
    }
}
