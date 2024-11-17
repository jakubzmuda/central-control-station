package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Distributions {
    private List<Distribution> distributionList;
    private Optional<Distribution> total;

    public Distributions(List<Distribution> distributionList) {
        this.distributionList = distributionList;
        this.total = distributionList.isEmpty() ? Optional.empty() : Optional.of(distributionList.getFirst());
    }

    public static Distributions empty() {
        return new Distributions(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distributions that = (Distributions) o;
        return Objects.equals(distributionList, that.distributionList) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distributionList, total);
    }

    @Override
    public String toString() {
        return "DistributionList{" +
                "distributionList=" + distributionList +
                ", total=" + total +
                '}';
    }

    public String productTicker() {
        return distributionList.getFirst().productTicker();
    }

    public List<Distribution> distributionList() {
        return distributionList;
    }

    public Distributions distributionsInMonth(String month) {
        return new Distributions(distributionList
                .stream()
                .filter(d -> d.exDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).equalsIgnoreCase(month))
                .toList());
    }

    public Distributions addAll(Distributions distributions) {
        return new Distributions(Stream.concat(distributionList.stream(), distributions.distributionList().stream())
                .collect(Collectors.toList()));
    }

    public Optional<Distribution> total() {
        return total;
    }

    public Optional<Distribution> last() {
        return distributionList.isEmpty() ? Optional.empty() : Optional.of(distributionList.getLast());
    }
}
