package com.tcc.tccapi.service;

import com.tcc.tccapi.domain.model.Employee;
import com.tcc.tccapi.domain.model.Payroll;
import com.tcc.tccapi.domain.model.PayrollEntry;
import com.tcc.tccapi.repository.PayrollEntryRepository;
import com.tcc.tccapi.repository.PayrollRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayrollService {

    private final PayrollEntryRepository entryRepo;
    private final PayrollRepository payrollRepo;
    private final PayrollCalculator calculator;

    public PayrollService(PayrollEntryRepository entryRepo,
                          PayrollRepository payrollRepo,
                          PayrollCalculator calculator) {
        this.entryRepo = entryRepo;
        this.payrollRepo = payrollRepo;
        this.calculator = calculator;
    }

    /**
     * Process payroll for a given period.
     * Runs inside a single transaction to avoid lock wait issues.
     * Uses saveAll for batch inserts/updates.
     */
    @Transactional
    public List<Payroll> processPeriod(String period) {
        // Fetch entries for this period
        List<PayrollEntry> entries = entryRepo.findByPeriod(period);

        // Build payrolls (skip if already processed for employee+period)
        List<Payroll> payrolls = entries.stream()
                .map(entry -> {
                    Employee employee = entry.getEmployee();

                    // Check if already processed
                    Payroll payroll = payrollRepo.findByEmployee_IdAndPeriod(employee.getId(), period)
                            .orElseGet(Payroll::new);

                    // Compute salaries
                    var result = calculator.compute(employee, entry);

                    payroll.setEmployee(employee);
                    payroll.setPeriod(period);
                    payroll.setGrossSalary(result.gross());
                    payroll.setInss(result.inss());
                    payroll.setIncomeTax(result.irrf());
                    payroll.setNetSalary(result.net());

                    return payroll;
                })
                .toList();

        // Save all in batch (configured via application.properties)
        return payrollRepo.saveAll(payrolls);
    }
}
