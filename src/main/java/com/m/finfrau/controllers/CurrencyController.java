package com.m.finfrau.controllers;

import com.m.finfrau.requests.CurrencyRequest;
import com.m.finfrau.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping ("/cnv")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<BigDecimal> convert(@RequestBody CurrencyRequest currency) {
        BigDecimal converted = currencyService.convert(currency.getFrom(), currency.getTo(),currency.getAmount());
        return ResponseEntity.ok(converted);
    }

}
