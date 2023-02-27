package org.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money{
    private BigDecimal amount;
    private Currency currency;
    private final RateProvider rateProvider;
    public Money(String amount, String currency) {
        this.amount = new BigDecimal(amount);
        this.currency = Currency.getInstance(currency);
        this.rateProvider = new RateProvider();
    }

    public BigDecimal convertCurrencyTo(Money moneyToConvert) {
        if (moneyToConvert.currency.getCurrencyCode().equals("PLN"))
            return amount.multiply(rateProvider.getPlnToCurrencyRate()).setScale(moneyToConvert.currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
        return amount.divide(rateProvider.getPlnToCurrencyRate(), RoundingMode.HALF_EVEN).setScale(moneyToConvert.currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
