package com.github.jakubzmuda.centralControlStation.investments.domain;

import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ActualDistributionsTest {

    @Test
    public void shouldHandleEmpty() {
        assertThat(ActualDistributions.empty().last()).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldSortByExDate() {
        MonetaryValue someValue = MonetaryValue.of(Currency.USD, 1f);
        String someProduct = "aapl";
        var actualDistributions = new ActualDistributions(List.of(
                new ActualDistribution(someProduct, someValue).withExDate(LocalDate.parse("2024-02-01")),
                new ActualDistribution(someProduct, someValue).withExDate(LocalDate.parse("2024-05-01")),
                new ActualDistribution(someProduct, someValue).withExDate(LocalDate.parse("2024-01-01"))
        ));

        assertThat(actualDistributions.last().get().exDate()).isEqualTo("2024-05-01");
    }
}
