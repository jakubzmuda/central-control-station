package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class StubbedProductDistributions {

    public Optional<ActualDistributions> get(String productTicker) {
        switch (productTicker) {
            case "domdev":
                return Optional.of(domdevDistributions());
            case "archicom":
                return Optional.of(archicomDistributions());
            default: return Optional.empty();
        }
    }

    private ActualDistributions archicomDistributions() {
        return new ActualDistributions(List.of(
                new ActualDistribution("archicom", MonetaryValue.of(Currency.PLN, 1.41f))
                        .withExDate(LocalDate.parse("2024-10-29"))
                        .withPayDate(LocalDate.parse("2024-11-07")),
                new ActualDistribution("archicom", MonetaryValue.of(Currency.PLN, 0.64f))
                        .withExDate(LocalDate.parse("2024-07-09"))
                        .withPayDate(LocalDate.parse("2024-07-25"))
        ));
    }

    private ActualDistributions domdevDistributions() {
        return new ActualDistributions(List.of(
                new ActualDistribution("domdev", MonetaryValue.of(Currency.PLN, 6f))
                        .withExDate(LocalDate.parse("2024-12-10"))
                        .withPayDate(LocalDate.parse("2024-12-18")),
                new ActualDistribution("domdev", MonetaryValue.of(Currency.PLN, 6.5f))
                        .withExDate(LocalDate.parse("2024-06-24"))
                        .withPayDate(LocalDate.parse("2024-07-04"))
        ));
    }
}
