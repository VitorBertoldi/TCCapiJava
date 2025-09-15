package com.tcc.tccapi.controller;

import com.tcc.tccapi.domain.model.Employee;
import com.tcc.tccapi.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository repo;
    public EmployeeController(EmployeeRepository repo){ this.repo = repo; }

    @PostMapping public Employee create(@RequestBody Employee e){ return repo.save(e); }
    @GetMapping public List<Employee> all(){ return repo.findAll(); }
    @GetMapping("/{id}") public Employee one(@PathVariable Long id){ return repo.findById(id).orElse(null); }
    @PutMapping("/{id}") public Employee update(@PathVariable Long id, @RequestBody Employee dto){
        return repo.findById(id).map(e -> {
            e.setName(dto.getName()); e.setDocument(dto.getDocument());
            e.setBaseSalary(dto.getBaseSalary()); e.setDependents(dto.getDependents());
            e.setUnionMember(dto.getUnionMember());
            return repo.save(e);
        }).orElse(null);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){ repo.deleteById(id); }
}
