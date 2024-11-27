package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRatesDataSupplier;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRatesService {

    private CurrencyRatesDataSupplier currenciesDataSupplier;
    private CurrentUser currentUser;

    public CurrencyRatesService(CurrencyRatesDataSupplier currenciesDataSupplier, CurrentUser currentUser) {
        this.currenciesDataSupplier = currenciesDataSupplier;
        this.currentUser = currentUser;
    }

    public CurrencyRates getRates() {
        currentUser.getOrUnauthorized();
        return currenciesDataSupplier.getCurrencyRates();
    }
}
