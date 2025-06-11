package com.m.finfrau.controllers;

import com.m.finfrau.repositories.UserRepository;
import com.m.finfrau.requests.ExpenseRequest;
import com.m.finfrau.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, Authentication authentication) {
        Long userId = (long) userRepository.findByUsername(authentication.getName()).get().getId();
        boolean deleted = expenseService.deleteExpense(userId,id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ExpenseRequest>> getExpenses(Authentication authentication) {
        Long userId = (long) userRepository.findByUsername(authentication.getName()).get().getId();
        List<ExpenseRequest> expenses = expenseService.getExpenses(userId);
        return ResponseEntity.ok(expenses);
    }
}
