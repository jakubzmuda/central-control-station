package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.application.CurrencyRatesService;
import com.github.jakubzmuda.centralControlStation.investments.application.PortfoliosService;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyRatesEndpoint {

    private CurrencyRatesService application;

    public CurrencyRatesEndpoint(CurrencyRatesService application) {
        this.application = application;
    }

    @GetMapping
    public CurrencyRatesResponse get() {
        return new CurrencyRatesResponse(application.getRates());
    }

    static class CurrencyRatesResponse {
        Map<String, Float> rates = new HashMap<>();

        private CurrencyRatesResponse() {
        }

        public CurrencyRatesResponse(CurrencyRates currencyRates) {
            rates.put("USD/PLN", currencyRates.rateFor(Currency.USD, Currency.PLN));
            rates.put("EUR/PLN", currencyRates.rateFor(Currency.EUR, Currency.PLN));
        }
    }
}