package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;

public record ForecastedDistribution(String productName, MonetaryValue monetaryValue, Month month) {

}
