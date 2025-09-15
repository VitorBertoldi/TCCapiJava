// com/tcc/tccapi/repository/PayrollEntryRepository.java
package com.tcc.tccapi.repository;
import com.tcc.tccapi.domain.model.PayrollEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayrollEntryRepository extends JpaRepository<PayrollEntry, Long> {
    List<PayrollEntry> findByPeriod(String period);
    Optional<PayrollEntry> findByEmployeeIdAndPeriod(Long employeeId, String period);
}
