package org.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
    private BigDecimal amount;
    private final Currency currency;
    private final RateProvider rateProvider;

    public Money(String amount, String currency, RateProvider rateProvider) {
        this.amount = new BigDecimal(amount);
        this.currency = Currency.getInstance(currency);
        this.rateProvider = rateProvider;
    }


    public void convertCurrencyFrom(Money moneyToConvert) {
        if (moneyToConvert.currency.getCurrencyCode().equals("PLN"))
            amount = moneyToConvert.amount.divide(rateProvider.getPlnToCurrencyRate(), RoundingMode.HALF_EVEN)
                    .setScale(moneyToConvert.currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
        else
            amount = moneyToConvert.amount.multiply(rateProvider.getPlnToCurrencyRate())
                    .setScale(moneyToConvert.currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount
                .setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public Currency getCurrency() {
        return currency;
    }

    public RateProvider getRateProvider() {
        return rateProvider;
    }
}
