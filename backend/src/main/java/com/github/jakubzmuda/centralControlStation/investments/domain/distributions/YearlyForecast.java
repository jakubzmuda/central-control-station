package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YearlyForecast {
    private List<MonthlyForecast> monthlyForecasts;

    public YearlyForecast(List<MonthlyForecast> monthlyForecasts) {
        this.monthlyForecasts = monthlyForecasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearlyForecast that = (YearlyForecast) o;
        return Objects.equals(monthlyForecasts, that.monthlyForecasts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(monthlyForecasts);
    }

    @Override
    public String toString() {
        return "YearlyForecast{" +
                "monthlyForecasts=" + monthlyForecasts +
                '}';
    }

    public List<MonthlyForecast> monthlyForecasts() {
        return monthlyForecasts;
    }

    public MultiCurrencyMonetaryValue total() {
       return new MultiCurrencyMonetaryValue(Map.of());
    }
}


//        return calculateTotalPerCurrency()
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        entry -> {
//                            var otherCurrencies = currencyRates.currencies()
//                                    .stream()
//                                    .filter(c -> c != entry.getKey())
//                                    .toList();
//
//                            otherCurrencies.
//
//                            return entry.getValue() + otherCurrencies.stream()
//
//
////                            return entry.getValue() * currencyRates.rateFor(entry.getKey(), Currency.USD);
//                        }
//                ));