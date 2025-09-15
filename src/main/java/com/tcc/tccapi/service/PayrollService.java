package com.tcc.tccapi.service;

import com.tcc.tccapi.domain.model.*;
import com.tcc.tccapi.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayrollService {
    private final PayrollEntryRepository entryRepo;
    private final PayrollRepository payrollRepo;
    private final PayrollCalculator calculator;

    public PayrollService(PayrollEntryRepository e, PayrollRepository p, PayrollCalculator c) {
        this.entryRepo = e; this.payrollRepo = p; this.calculator = c;
    }

    @Transactional
    public List<Payroll> processPeriod(String period) {
        var entries = entryRepo.findByPeriod(period);

        return entries.stream().map(in -> {
            var e = in.getEmployee();
            var r = calculator.compute(e, in);

            var payroll = payrollRepo.findByEmployee_IdAndPeriod(e.getId(), period)
                    .orElseGet(Payroll::new);

            payroll.setEmployee(e);
            payroll.setPeriod(period);
            payroll.setGrossSalary(r.gross());
            payroll.setInss(r.inss());
            payroll.setIncomeTax(r.irrf());
            payroll.setNetSalary(r.net());

            return payrollRepo.save(payroll);
        }).toList();
    }
}
