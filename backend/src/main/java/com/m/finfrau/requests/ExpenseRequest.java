package com.m.finfrau.requests;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private Long id;
    private BigDecimal amount;
    private String category;
    private String description;
    private String currency;
    private LocalDate expenseDate;

    public ExpenseRequest() {
        currency = "";
        amount = BigDecimal.ZERO;
        category = "";
        description = "";
        expenseDate = null;
    }

    ExpenseRequest(BigDecimal amount, String category, String description, String currency, LocalDate expenseDate) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.currency = currency;
        this.expenseDate = expenseDate;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}