package com.m.finfrau.service;

import com.m.finfrau.repositories.ExpenseRepository;
import com.m.finfrau.requests.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void addExpense(Long userId, ExpenseRequest request) {
        expenseRepository.saveExpense(userId, request);
    }

    public List<ExpenseRequest> getExpenses(Long userId) {
        return expenseRepository.getExpensesByUser(userId);
    }
}
