package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRatesDataSupplier;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesService {

    private CurrencyRatesDataSupplier currenciesDataSupplier;

    public CurrenciesService(CurrencyRatesDataSupplier currenciesDataSupplier) {
        this.currenciesDataSupplier = currenciesDataSupplier;
    }

    public CurrencyRates getRates() {
        return currenciesDataSupplier.getCurrencyRates();
    }
}
