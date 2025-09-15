package com.tcc.tccapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payrolls", uniqueConstraints = {
        @UniqueConstraint(name="uk_payroll_emp_period", columnNames={"employee_id","period"})
})
public class Payroll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable=false)
    @JsonIgnore
    private Employee employee;

    @Column(nullable=false, length=7) private String period;

    @Column(nullable=false, precision=10, scale=2) private BigDecimal grossSalary;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal inss;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal incomeTax;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal netSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("employeeId")
    public Long getEmployeeId() {
        return (employee != null) ? employee.getId() : null;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public BigDecimal getInss() {
        return inss;
    }

    public void setInss(BigDecimal inss) {
        this.inss = inss;
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }
}
