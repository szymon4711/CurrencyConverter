package org.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money should")
public class MoneyTest {

    private static RateProvider rateProvider;

    @BeforeAll
    public static void setUp() {
        rateProvider = Mockito.mock(RateProvider.class);
        Mockito.when(rateProvider.getPlnToCurrencyRate()).thenReturn(new BigDecimal("4.00"));
    }

    @Test
    @DisplayName("convert currency from EUR to PLN")
    public void testConvertCurrencyFromEurToPln() {
        Money moneyFrom = new Money("20.00", "EUR", rateProvider);
        Money moneyTo = new Money("0.00", "PLN", rateProvider);
        moneyTo.convertCurrencyFrom(moneyFrom);
        assertEquals(new BigDecimal("80.00"), moneyTo.getAmount());
    }

    @Test
    @DisplayName("convert currency from PLN to EUR")
    public void testConvertCurrencyFromPlnToEur() {
        Money moneyFrom = new Money("80.00", "PLN", rateProvider);
        Money moneyTo = new Money("0.00", "EUR", rateProvider);
        moneyTo.convertCurrencyFrom(moneyFrom);
        assertEquals(new BigDecimal("20.00"), moneyTo.getAmount());
    }
}
