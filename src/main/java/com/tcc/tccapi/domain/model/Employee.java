package com.tcc.tccapi.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "employees", indexes = {
        @Index(name="ix_employee_document", columnList="document", unique = true)
})
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true, length=20) private String document;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal baseSalary;
    @Column(nullable=false) private Integer dependents = 0;
    @Column(nullable=false) private Boolean unionMember = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getDependents() {
        return dependents;
    }

    public void setDependents(Integer dependents) {
        this.dependents = dependents;
    }

    public Boolean getUnionMember() {
        return unionMember;
    }

    public void setUnionMember(Boolean unionMember) {
        this.unionMember = unionMember;
    }
}
