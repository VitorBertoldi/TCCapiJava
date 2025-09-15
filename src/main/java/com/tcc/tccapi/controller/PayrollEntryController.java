package com.tcc.tccapi.controller;

import com.tcc.tccapi.domain.model.PayrollEntry;
import com.tcc.tccapi.repository.PayrollEntryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/payroll-entries")
public class PayrollEntryController {
    private final PayrollEntryRepository repo;
    public PayrollEntryController(PayrollEntryRepository repo){ this.repo = repo; }

    @PostMapping public PayrollEntry create(@RequestBody PayrollEntry e){ return repo.save(e); }
    @GetMapping public List<PayrollEntry> all(){ return repo.findAll(); }
    @GetMapping("/period/{p}") public List<PayrollEntry> byPeriod(@PathVariable("p") String period){ return repo.findByPeriod(period); }
}
