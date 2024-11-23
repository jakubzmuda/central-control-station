package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;

import java.util.List;
import java.util.Objects;

public final class MonthlyForecast {
    private Month month;
    private List<ForecastedDistribution> forecastedDistributions;
    private CurrencyRates currencyRates;

    public MonthlyForecast(Month month, List<ForecastedDistribution> forecastedDistributions, CurrencyRates currencyRates) {
        this.month = month;
        this.forecastedDistributions = forecastedDistributions;
        this.currencyRates = currencyRates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MonthlyForecast) obj;
        return Objects.equals(this.month, that.month) &&
                Objects.equals(this.forecastedDistributions, that.forecastedDistributions) &&
                Objects.equals(this.currencyRates, that.currencyRates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, forecastedDistributions, currencyRates);
    }

    @Override
    public String toString() {
        return "MonthlyForecast[" +
                "month=" + month + ", " +
                "forecastedDistributions=" + forecastedDistributions + ", " +
                "currencyRates=" + currencyRates + ']';
    }

    public Month month() {
        return month;
    }

    public List<ForecastedDistribution> forecastedDistributions() {
        return forecastedDistributions;
    }


    public MultiCurrencyMonetaryValue total() {
        return null;
    }
}
