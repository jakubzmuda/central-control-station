package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Distributions {
    private List<Distribution> distributions;
    private Optional<Distribution> total;

    public Distributions(List<Distribution> distributions) {
        this.distributions = distributions;
        this.total = distributions.isEmpty() ? Optional.empty() : Optional.of(distributions.getFirst());
    }

    public static Distributions empty() {
        return new Distributions(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distributions that = (Distributions) o;
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

    public String productTicker() {
        return distributions.getFirst().productTicker();
    }

    public List<Distribution> distributions() {
        return distributions;
    }

    public Optional<Distribution> total() {
        return total;
    }
}
