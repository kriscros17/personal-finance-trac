package com.finance.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category is required")
    @Column(nullable = false, unique = true)
    private String category;

    @NotNull(message = "Monthly limit is required")
    @Positive(message = "Limit must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyLimit;

    public Budget() {}

    public Budget(Long id, String category, BigDecimal monthlyLimit) {
        this.id = id;
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(BigDecimal monthlyLimit) { this.monthlyLimit = monthlyLimit; }
}
