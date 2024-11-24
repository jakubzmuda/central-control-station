package com.github.jakubzmuda.centralControlStation.investments.domain.currency;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CurrencyRatesTest {

    @Test
    public void shouldBeAbleToReturnRateInBothDirections() {
        CurrencyRates currencyRates = new CurrencyRates(Map.of(
                Tuple.of(Currency.USD, Currency.PLN), 4f
        ));

        assertThat(currencyRates.rateFor(Currency.USD, Currency.PLN)).isEqualTo(4f);
        assertThat(currencyRates.rateFor(Currency.PLN, Currency.USD)).isEqualTo(0.25f);
    }

}