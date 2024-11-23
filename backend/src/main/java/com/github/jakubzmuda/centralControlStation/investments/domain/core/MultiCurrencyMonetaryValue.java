package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;

import java.util.HashMap;
import java.util.Map;

public class MultiCurrencyMonetaryValue {

    private Map<Currency, Float> currencyToAmountMap = new HashMap<>();

    public MultiCurrencyMonetaryValue(Map<Currency, Float> currencyToAmountMap) {
        this.currencyToAmountMap = currencyToAmountMap;
    }

    public Float amountForCurrency(Currency currency) {
        return currencyToAmountMap.get(currency);
    }

    public Map<Currency, Float> asMap() {
        return currencyToAmountMap;
    }

    public MultiCurrencyMonetaryValue add(MultiCurrencyMonetaryValue multiCurrencyMonetaryValue) {
        Map<Currency, Float> newMap = new HashMap<>(currencyToAmountMap);
        multiCurrencyMonetaryValue.currencyToAmountMap.forEach((currency, amount) -> {
            newMap.put(currency, newMap.getOrDefault(currency, 0f) + amount);
        });
        return new MultiCurrencyMonetaryValue(newMap);
    }
}
