package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PortfolioEntry {
    String name;
    float amount;

    public PortfolioEntry(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioEntry that = (PortfolioEntry) o;
        return Float.compare(amount, that.amount) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "PortfolioEntry{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    public String name() {
        return name;
    }

    public float amount() {
        return amount;
    }
}
