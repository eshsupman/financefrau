package com.m.finfrau.requests;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDate expenseDate;

    public ExpenseRequest() {
        amount = BigDecimal.ZERO;
        category = "";
        description = "";
        expenseDate = null;
    }

    ExpenseRequest(BigDecimal amount, String category, String description, LocalDate expenseDate) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.expenseDate = expenseDate;
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

    public LocalDate getExpenseDate() {
        return expenseDate;
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
}