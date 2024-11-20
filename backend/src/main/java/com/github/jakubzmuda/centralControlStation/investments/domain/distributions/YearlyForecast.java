package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class YearlyForecast {
    LinkedHashMap<String, List<ForecastedDistribution>> months;

    public YearlyForecast(LinkedHashMap<String, List<ForecastedDistribution>> months) {
        this.months = months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearlyForecast that = (YearlyForecast) o;
        return Objects.equals(months, that.months);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(months);
    }

    @Override
    public String toString() {
        return "YearlyForecast{" +
                "months=" + months +
                '}';
    }

    public LinkedHashMap<String, List<ForecastedDistribution>> months() {
        return months;
    }

    public MonetaryValue total() {
        return MonetaryValue.of(Currency.USD, 0f);
    }
}
