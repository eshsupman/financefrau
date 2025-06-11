package com.m.finfrau.controllers;

import com.m.finfrau.repositories.UserRepository;
import com.m.finfrau.requests.ExpenseRequest;
import com.m.finfrau.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> addExpense(@RequestBody ExpenseRequest request, Authentication authentication) {
        Long userId = (long) userRepository.findByUsername(authentication.getName()).get().getId();
        expenseService.addExpense(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ExpenseRequest>> getExpenses(Authentication authentication) {
        Long userId = (long) userRepository.findByUsername(authentication.getName()).get().getId();
        List<ExpenseRequest> expenses = expenseService.getExpenses(userId);
        return ResponseEntity.ok(expenses);
    }
}
