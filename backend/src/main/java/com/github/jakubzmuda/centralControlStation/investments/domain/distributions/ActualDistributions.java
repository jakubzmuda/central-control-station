package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActualDistributions {
    private List<ActualDistribution> distributionList;

    public ActualDistributions(List<ActualDistribution> distributionList) {
        this.distributionList = distributionList;
    }

    public static ActualDistributions empty() {
        return new ActualDistributions(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActualDistributions that = (ActualDistributions) o;
        return Objects.equals(distributionList, that.distributionList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(distributionList);
    }

    @Override
    public String toString() {
        return "Distributions{" +
                "distributionList=" + distributionList +
                '}';
    }

    public String productTicker() {
        return distributionList.getFirst().productTicker();
    }

    public List<ActualDistribution> distributionList() {
        return distributionList;
    }

    public ActualDistributions inMonth(String month) {
        return new ActualDistributions(distributionList
                .stream()
                .filter(d -> d.exDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).equalsIgnoreCase(month))
                .toList());
    }

    public ActualDistributions forProduct(String productTicker) {
        return new ActualDistributions(distributionList
                .stream()
                .filter(d -> d.productTicker().equalsIgnoreCase(productTicker))
                .toList());
    }

    public ActualDistributions lastYear() {
        return new ActualDistributions(distributionList
                .stream()
                .filter(d -> {
                    LocalDate today = LocalDate.now();
                    LocalDate yearFromNow = today.minusYears(1)
                            .minusDays(30); // safety factor
                    return d.exDate().isAfter(yearFromNow) && d.exDate().isBefore(today);
                })
                .toList());
    }

    public ActualDistributions addAll(ActualDistributions distributions) {
        return new ActualDistributions(Stream.concat(distributionList.stream(), distributions.distributionList().stream())
                .collect(Collectors.toList()));
    }

    public Optional<ActualDistribution> last() {
        return distributionList.isEmpty() ? Optional.empty() : Optional.of(distributionList.getLast());
    }
}
