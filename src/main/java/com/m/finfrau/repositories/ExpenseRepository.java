package com.m.finfrau.repositories;


import com.m.finfrau.requests.ExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class ExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveExpense(Long userId, ExpenseRequest expense) {
        String sql = "INSERT INTO expenses (user_id, amount, category, description, expense_date, currency) " +
                "VALUES (?, ?, ?, ?, ?,?)";
        System.out.println(expense.getCurrency());
        jdbcTemplate.update(sql, userId, expense.getAmount(), expense.getCategory(),
                expense.getDescription(), expense.getExpenseDate(), expense.getCurrency());
    }

    public void deleteExpense(Long userId, Long expenseId) {
        String sql = "DELETE FROM expenses WHERE user_id = ? AND id = ?";
        jdbcTemplate.update(sql, userId, expenseId);
    }

    public List<ExpenseRequest> getExpensesByUser(Long userId) {
        String sql = "SELECT id, amount, category, description, expense_date, currency FROM expenses WHERE user_id = ? AND expense_date = ?";
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return jdbcTemplate.query(sql, new Object[]{userId, sqlDate}, (rs, rowNum) -> {
            ExpenseRequest response = new ExpenseRequest();
            response.setId(rs.getLong("id"));
            response.setAmount(rs.getBigDecimal("amount"));
            response.setCategory(rs.getString("category"));
            response.setDescription(rs.getString("description"));
            response.setExpenseDate(rs.getDate("expense_date").toLocalDate());
            response.setCurrency(rs.getString("currency"));
            return response;
        });
    }
}
