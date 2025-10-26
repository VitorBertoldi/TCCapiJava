package com.tcc.tccapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.tccapi.service.PayrollService;
import com.tcc.tccapi.service.PayrollService.PayrollResult;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("/stress/fake")
    public List<PayrollResult> generatePayrolls(
            @RequestParam(name = "count", defaultValue = "1000") int count,
            @RequestParam(name = "period", defaultValue = "2025-08") String period) {
        return payrollService.generatePayrolls(count, period);
    }
}
