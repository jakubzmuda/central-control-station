package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;

@Entity
public class Portfolio {

    @Id
    private UserId userId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<PortfolioEntry> entries;

    private Portfolio() {
    }

    public Portfolio(UserId userId, List<PortfolioEntry> entries) {
        this.userId = userId;
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

    public UserId userId() {
        return userId;
    }

    public List<PortfolioEntry> entries() {
        return entries;
    }
}
