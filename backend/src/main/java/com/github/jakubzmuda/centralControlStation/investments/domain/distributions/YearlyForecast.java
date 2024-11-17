package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class YearlyForecast {
    LinkedHashMap<String, Distributions> months;
    Distribution total;

    public YearlyForecast(LinkedHashMap<String, Distributions> months) {
        this.months = months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearlyForecast that = (YearlyForecast) o;
        return Objects.equals(months, that.months) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(months, total);
    }

    @Override
    public String toString() {
        return "YearlyForecast{" +
                "months=" + months +
                ", total=" + total +
                '}';
    }

    public Map<String, Distributions> months() {
        return months;
    }

    public Distribution total() {
        return total;
    }
}
