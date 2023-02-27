package org.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;

public class RateProvider {
    private BigDecimal rate;

    public RateProvider() {
        String URL = "https://api.nbp.pl/api/exchangerates/rates/a/gbp/?format=json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String rate = objectMapper
                    .readTree(new URL(URL))
                    .get("rates")
                    .get(0)
                    .get("mid")
                    .toString();
            this.rate = new BigDecimal(rate);
        } catch (IOException e) {
            System.err.println("Cannot access rates from NBP API");
        }

    }

    public BigDecimal getPlnToCurrencyRate() {
        return rate;
    }
}

