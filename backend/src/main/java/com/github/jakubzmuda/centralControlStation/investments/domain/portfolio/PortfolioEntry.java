package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PortfolioEntry {
    String productTicker;
    float amount;

    private PortfolioEntry() {
    }

    public PortfolioEntry(String productTicker, float amount) {
        this.productTicker = productTicker;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioEntry that = (PortfolioEntry) o;
        return Float.compare(amount, that.amount) == 0 && Objects.equals(productTicker, that.productTicker);
    }

    @Override
    public String toString() {
        return "PortfolioEntry{" +
                "name='" + productTicker + '\'' +
                ", monetaryValue=" + amount +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(productTicker, amount);
    }

    public String productTicker() {
        return productTicker;
    }

    public float amount() {
        return amount;
    }
}
