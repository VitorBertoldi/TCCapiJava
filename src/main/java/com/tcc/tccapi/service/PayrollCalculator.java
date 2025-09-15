package com.tcc.tccapi.service;

import com.tcc.tccapi.domain.model.Employee;
import com.tcc.tccapi.domain.model.PayrollEntry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PayrollCalculator {
    // Valor da hora simplificado: base / 220
    private BigDecimal hourlyRate(Employee e){
        return e.getBaseSalary().divide(BigDecimal.valueOf(220), 2, RoundingMode.HALF_EVEN);
    }

    // INSS/IRRF simplificados só p/ doc/benchmark
    private BigDecimal calcINSS(BigDecimal base){
        // Exemplo simples: 11% com teto fictício
        BigDecimal inss = base.multiply(BigDecimal.valueOf(0.11));
        BigDecimal teto = new BigDecimal("750.00");
        return inss.min(teto).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calcIRRF(BigDecimal base, int dependents){
        BigDecimal depDed = BigDecimal.valueOf(189.59).multiply(BigDecimal.valueOf(dependents));
        BigDecimal taxable = base.subtract(depDed).max(BigDecimal.ZERO);
        // Faixa simples: 7.5% acima de 2500
        if (taxable.compareTo(BigDecimal.valueOf(2500)) <= 0) return BigDecimal.ZERO.setScale(2);
        return taxable.subtract(BigDecimal.valueOf(2500))
                .multiply(BigDecimal.valueOf(0.075))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public Result compute(Employee e, PayrollEntry in){
        BigDecimal base = e.getBaseSalary();
        BigDecimal overtime = hourlyRate(e).multiply(in.getOvertimeHours());
        BigDecimal gross = base.add(overtime).add(in.getBonus()).subtract(in.getDiscounts());

        BigDecimal inss = calcINSS(gross);
        BigDecimal irrf = calcIRRF(gross.subtract(inss), e.getDependents());
        BigDecimal net  = gross.subtract(inss).subtract(irrf);

        return new Result(gross, inss, irrf, net);
    }

    public record Result(BigDecimal gross, BigDecimal inss, BigDecimal irrf, BigDecimal net){}
}
