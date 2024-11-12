package com.github.jakubzmuda.centralControlStation.investments.application;

import java.util.Map;
import java.util.Objects;

public class YearlyForecast {
    Map<String, DistributionList> months;
    Distribution total;

    public YearlyForecast(Map<String, DistributionList> months) {
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

    public Map<String, DistributionList> months() {
        return months;
    }

    public Distribution total() {
        return total;
    }
}
