package com.tcc.tccapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PayrollService {

    private final PayrollCalculator payrollCalculator;

    public PayrollService(PayrollCalculator payrollCalculator) {
        this.payrollCalculator = payrollCalculator;
    }

    public List<PayrollResult> generatePayrolls(int count, String period) {
        int safeCount = Math.max(count, 1);
        List<PayrollResult> results = new ArrayList<>(safeCount);

        for (int i = 1; i <= safeCount; i++) {
            EmployeeSnapshot snapshot = buildEmployeeSnapshot(i);
            PayrollCalculator.CalculationResult calculation = payrollCalculator.calculate(
                    snapshot.baseSalary,
                    snapshot.overtimeHours,
                    snapshot.bonus,
                    snapshot.discounts,
                    snapshot.dependents);

            results.add(new PayrollResult(
                    i,
                    period,
                    calculation.grossSalary(),
                    calculation.inss(),
                    calculation.incomeTax(),
                    calculation.netSalary()));
        }

        return results;
    }

    private EmployeeSnapshot buildEmployeeSnapshot(int employeeId) {
        double baseSalary = 1800.0 + (employeeId % 12) * 220.0 + (employeeId % 5) * 47.35;
        double overtimeHours = (employeeId % 15) * 1.25;
        double bonus = (employeeId % 7) * 150.5;
        double discounts = (employeeId % 4) * 95.75;
        int dependents = employeeId % 3;
        return new EmployeeSnapshot(baseSalary, overtimeHours, bonus, discounts, dependents);
    }

    private record EmployeeSnapshot(
            double baseSalary,
            double overtimeHours,
            double bonus,
            double discounts,
            int dependents) {
    }

    public record PayrollResult(
            int employeeId,
            String period,
            double grossSalary,
            double inss,
            double incomeTax,
            double netSalary) {
    }
}
