package com.tcc.tccapi.repository;
import com.tcc.tccapi.domain.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Optional<Payroll> findByEmployee_IdAndPeriod(Long employeeId, String period);
    List<Payroll> findByPeriod(String period);
}
