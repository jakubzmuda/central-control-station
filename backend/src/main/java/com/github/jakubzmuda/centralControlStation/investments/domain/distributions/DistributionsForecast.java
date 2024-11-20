package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import java.util.Objects;

public class DistributionsForecast {
    private YearlyForecast yearlyForecast;

    public DistributionsForecast(YearlyForecast yearlyForecast) {
        this.yearlyForecast = yearlyForecast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionsForecast that = (DistributionsForecast) o;
        return Objects.equals(yearlyForecast, that.yearlyForecast);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(yearlyForecast);
    }

    @Override
    public String toString() {
        return "DistributionForecast{" +
                "yearlyForecast=" + yearlyForecast +
                '}';
    }

    public YearlyForecast yearlyForecast() {
        return yearlyForecast;
    }
}