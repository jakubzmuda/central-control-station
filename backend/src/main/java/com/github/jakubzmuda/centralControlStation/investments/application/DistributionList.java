package com.github.jakubzmuda.centralControlStation.investments.application;

import java.util.List;
import java.util.Objects;

public class DistributionList {
    private List<Distribution> distributions;
    private Distribution total;

    public DistributionList(List<Distribution> distributions, Distribution total) {
        this.distributions = distributions;
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionList that = (DistributionList) o;
        return Objects.equals(distributions, that.distributions) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distributions, total);
    }

    @Override
    public String toString() {
        return "DistributionList{" +
                "distributions=" + distributions +
                ", total=" + total +
                '}';
    }

    public List<Distribution> distributions() {
        return distributions;
    }

    public Distribution total() {
        return total;
    }
}
