package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;

import java.util.List;

public record YearlyForecast(List<MonthlyForecast> monthlyForecasts) {

    public MultiCurrencyMonetaryValue total() {
        return this.monthlyForecasts
                .stream()
                .map(MonthlyForecast::totalForEachCurrency)
                .reduce(MultiCurrencyMonetaryValue::add)
                .orElseThrow(() -> new RuntimeException("No monthly forecasts"));
    }
}