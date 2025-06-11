package com.m.finfrau.service;

import com.m.finfrau.requests.ValCurs;
import com.m.finfrau.requests.Valute;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {

    private static final String URL = "https://cbr.ru/scripts/XML_daily.asp";

    private final RestTemplate restTemplate = new RestTemplate();
    private Map<String, BigDecimal> cachedRates;
    private LocalDate lastUpdated;
    private Map<String, BigDecimal> fetchRates() {
        try {
            String xml = restTemplate.getForObject(URL, String.class);

            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(new StringReader(xml));

            Map<String, BigDecimal> rates = new HashMap<>();

            for (Valute valute : valCurs.getValuteList()) {
                BigDecimal value = new BigDecimal(valute.getValue().replace(",", "."));
                BigDecimal ratePerOne = value.divide(BigDecimal.valueOf(valute.getNominal()), 10, RoundingMode.HALF_UP);
                rates.put(valute.getCharCode(), ratePerOne);
            }

            rates.put("RUB", BigDecimal.ONE);
            return rates;

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("RUB", BigDecimal.ONE);
        }
    }

    public Map<String, BigDecimal> getCurrencyRates() {
        if (cachedRates == null || !LocalDate.now().equals(lastUpdated)) {
            cachedRates = fetchRates();
            lastUpdated = LocalDate.now();
        }
        return cachedRates;
    }

    public BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount) {
        Map<String, BigDecimal> rates = getCurrencyRates();

        BigDecimal fromRate = rates.getOrDefault(fromCurrency, BigDecimal.ONE);
        BigDecimal toRate = rates.getOrDefault(toCurrency, BigDecimal.ONE);
        System.out.println(fromRate);
        System.out.println(toRate);

        return amount.multiply(fromRate)
                .divide(toRate, 4, RoundingMode.HALF_UP);
    }
}
