package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MonthlyForecast {
    private Month month;
    private List<ForecastedDistribution> forecastedDistributions;

    public MonthlyForecast(Month month, List<ForecastedDistribution> forecastedDistributions) {
        this.month = month;
        this.forecastedDistributions = forecastedDistributions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MonthlyForecast) obj;
        return Objects.equals(this.month, that.month) &&
                Objects.equals(this.forecastedDistributions, that.forecastedDistributions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, forecastedDistributions);
    }

    @Override
    public String toString() {
        return "MonthlyForecast{" +
                "month=" + month +
                ", forecastedDistributions=" + forecastedDistributions +
                '}';
    }

    public Month month() {
        return month;
    }

    public List<ForecastedDistribution> forecastedDistributions() {
        return forecastedDistributions;
    }

    public Optional<MultiCurrencyMonetaryValue> totalForEachCurrency() {
        return forecastedDistributions
                .stream()
                .map(ForecastedDistribution::monetaryValue)
                .map(MultiCurrencyMonetaryValue::new)
                .reduce(MultiCurrencyMonetaryValue::add);
    }
}
