package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;

public record ForecastedDistribution(String productName, MonetaryValue monetaryValue) {

}
