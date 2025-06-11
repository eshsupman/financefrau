package com.m.finfrau.repositories;


import com.m.finfrau.requests.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveExpense(Long userId, ExpenseRequest expense) {
        String sql = "INSERT INTO expenses (user_id, amount, category, description, expense_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, expense.getAmount(), expense.getCategory(),
                expense.getDescription(), expense.getExpenseDate());
    }

    public List<ExpenseRequest> getExpensesByUser(Long userId) {
        String sql = "SELECT amount, category, description, expense_date FROM expenses WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            ExpenseRequest response = new ExpenseRequest();
            //response.setId(rs.getLong("id"));
            response.setAmount(rs.getBigDecimal("amount"));
            response.setCategory(rs.getString("category"));
            response.setDescription(rs.getString("description"));
            response.setExpenseDate(rs.getDate("expense_date").toLocalDate());
            return response;
        });
    }
}
