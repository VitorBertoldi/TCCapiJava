package com.tcc.tccapi.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_entries", uniqueConstraints = {
        @UniqueConstraint(name="uk_entry_emp_period", columnNames={"employee_id","period"})
})
public class PayrollEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    // formato YYYY-MM (ex.: "2025-08")
    @Column(nullable=false, length=7) private String period;

    @Column(precision=10, scale=2) private BigDecimal overtimeHours = BigDecimal.ZERO;
    @Column(precision=10, scale=2) private BigDecimal bonus = BigDecimal.ZERO;
    @Column(precision=10, scale=2) private BigDecimal discounts = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
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

    public BigDecimal getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(BigDecimal overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public BigDecimal getDiscounts() {
        return discounts;
    }

    public void setDiscounts(BigDecimal discounts) {
        this.discounts = discounts;
    }
}
