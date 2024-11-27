package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;

import java.util.List;
import java.util.Optional;

public record YearlyForecast(List<MonthlyForecast> monthlyForecasts) {

    public Optional<MultiCurrencyMonetaryValue> total() {
        return this.monthlyForecasts
                .stream()
                .map(MonthlyForecast::totalForEachCurrency)
                .flatMap(Optional::stream)
                .reduce(MultiCurrencyMonetaryValue::add);
    }
}