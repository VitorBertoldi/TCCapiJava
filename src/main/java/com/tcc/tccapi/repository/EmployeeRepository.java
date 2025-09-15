package com.tcc.tccapi.repository;
import com.tcc.tccapi.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByDocument(String document);
}
