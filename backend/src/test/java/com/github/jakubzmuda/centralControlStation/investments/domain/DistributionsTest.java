package com.github.jakubzmuda.centralControlStation.investments.domain;

import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.Distributions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DistributionsTest {

    @Test
    public void shouldHandleEmpty() {
        assertThat(Distributions.empty().total()).isEqualTo(Optional.empty());
    }
}
