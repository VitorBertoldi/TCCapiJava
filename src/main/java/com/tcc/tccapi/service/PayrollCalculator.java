package com.tcc.tccapi.service;

import org.springframework.stereotype.Component;

@Component
public class PayrollCalculator {

    public CalculationResult calculate(double baseSalary, double overtimeHours, double bonus, double discounts, int dependents) {
        double overtimeRate = baseSalary / 160.0 * 1.5;
        double overtimePay = overtimeHours * overtimeRate;
        double grossSalary = baseSalary + overtimePay + bonus - discounts;
        if (grossSalary < 0) {
            grossSalary = 0;
        }

        double inss = calculateInss(grossSalary);
        double incomeTax = calculateIncomeTax(grossSalary, inss, dependents);
        double netSalary = grossSalary - inss - incomeTax;
        if (netSalary < 0) {
            netSalary = 0;
        }

        return new CalculationResult(grossSalary, inss, incomeTax, netSalary);
    }

    private double calculateInss(double grossSalary) {
        double rate;
        if (grossSalary <= 1412.00) {
            rate = 0.075;
        } else if (grossSalary <= 2666.68) {
            rate = 0.09;
        } else if (grossSalary <= 4000.03) {
            rate = 0.12;
        } else {
            rate = 0.14;
        }
        return grossSalary * rate;
    }

    private double calculateIncomeTax(double grossSalary, double inss, int dependents) {
        double taxableIncome = grossSalary - inss - dependents * 189.59;
        if (taxableIncome <= 0) {
            return 0;
        }

        double rate;
        double deduction;
        if (taxableIncome <= 2112.00) {
            rate = 0;
            deduction = 0;
        } else if (taxableIncome <= 2826.65) {
            rate = 0.075;
            deduction = 158.40;
        } else if (taxableIncome <= 3751.05) {
            rate = 0.15;
            deduction = 370.40;
        } else if (taxableIncome <= 4664.68) {
            rate = 0.225;
            deduction = 651.73;
        } else {
            rate = 0.275;
            deduction = 884.96;
        }

        double incomeTax = taxableIncome * rate - deduction;
        if (incomeTax < 0) {
            return 0;
        }
        return incomeTax;
    }

    public record CalculationResult(double grossSalary, double inss, double incomeTax, double netSalary) {
    }
}
