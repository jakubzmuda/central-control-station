package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import java.util.List;
import java.util.Objects;

public class Portfolio {

    List<PortfolioEntry> entries;

    public Portfolio(List<PortfolioEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return Objects.equals(entries, portfolio.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entries);
    }

    public List<PortfolioEntry> entries() {
        return entries;
    }
}
