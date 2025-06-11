package com.m.finfrau.requests;

import java.math.BigDecimal;

public class CurrencyRequest {
    private String to;
    private String from;
    private BigDecimal amount;

    CurrencyRequest(){
        this.to = "";
        this.from = "";
        this.amount = null;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getTo() {
        return to;
    }
    public String getFrom() {
        return from;
    }
    public BigDecimal getAmount() {
        return amount;
    }
}
