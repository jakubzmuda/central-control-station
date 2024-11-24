package com.github.jakubzmuda.centralControlStation.investments.domain.currency;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Tuple;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CurrencyRates {

    private final Map<Tuple<Currency>, Float> rates;

    public CurrencyRates(Map<Tuple<Currency>, Float> rates) {
        this.rates = rates;
    }

    public Set<Currency> currencies() {
        return rates
                .keySet()
                .stream()
                .flatMap(tuple -> Set.of(tuple.first(), tuple.second()).stream())
                .collect(Collectors.toSet());
    }

    public Float rateFor(Currency first, Currency second) {
        Float submittedRate = rates.get(Tuple.of(first, second));
        return submittedRate != null ? submittedRate : 1 / rates.get(Tuple.of(second, first));
    }
}
