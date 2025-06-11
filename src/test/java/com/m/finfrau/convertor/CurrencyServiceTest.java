package com.m.finfrau.convertor;

import com.m.finfrau.requests.ValCurs;
import com.m.finfrau.requests.Valute;
import com.m.finfrau.service.CurrencyService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CurrencyServiceTest {

    private RestTemplate restTemplate;
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        currencyService = new CurrencyService(restTemplate);
    }

    @Test
    void testConvert_withMockedRates() throws Exception {
        String xmlResponse = """
                <ValCurs Date="11.06.2025" name="Foreign Currency Market">
                   <Valute ID="R01235">
                      <NumCode>840</NumCode>
                      <CharCode>USD</CharCode>
                      <Nominal>1</Nominal>
                      <Name>Доллар США</Name>
                      <Value>90,0000</Value>
                   </Valute>
                </ValCurs>
                """;

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(xmlResponse);

        BigDecimal result = currencyService.convert("RUB", "USD", BigDecimal.valueOf(900));
        assertEquals(BigDecimal.valueOf(10).setScale(4), result);
    }

    @Test
    void testConvert_sameCurrency() throws Exception {
        BigDecimal result = currencyService.convert("RUB", "RUB", BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500).setScale(4), result);
    }

    @Test
    void testConvert_unknownCurrency() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("");

        BigDecimal result = currencyService.convert("XXX", "YYY", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100.0000).setScale(4), result);
    }

    @Test
    void testCachingWorks() throws Exception {
        String xmlResponse = """
                <ValCurs Date="11.06.2025" name="Foreign Currency Market">
                   <Valute ID="R01235">
                      <NumCode>840</NumCode>
                      <CharCode>USD</CharCode>
                      <Nominal>1</Nominal>
                      <Name>Доллар США</Name>
                      <Value>100,0000</Value>
                   </Valute>
                </ValCurs>
                """;

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(xmlResponse);

        currencyService.convert("RUB", "USD", BigDecimal.valueOf(100));
        currencyService.convert("RUB", "USD", BigDecimal.valueOf(100));

        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
    }
}
