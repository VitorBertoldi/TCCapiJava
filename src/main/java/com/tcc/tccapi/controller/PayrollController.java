// com/tcc/tccapi/controller/PayrollController.java
package com.tcc.tccapi.controller;

import com.tcc.tccapi.domain.model.Payroll;
import com.tcc.tccapi.repository.PayrollRepository;
import com.tcc.tccapi.service.PayrollService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/payrolls")
public class PayrollController {
    private final PayrollRepository repo;
    private final PayrollService service;
    public PayrollController(PayrollRepository repo, PayrollService service){
        this.repo = repo; this.service = service;
    }

    @PostMapping("/process")
    public List<Payroll> process(@RequestParam String period){
        return service.processPeriod(period);
    }

    @GetMapping public List<Payroll> all(){ return repo.findAll(); }
    @GetMapping("/period/{p}") public List<Payroll> byPeriod(@PathVariable("p") String period){ return repo.findByPeriod(period); }
}
