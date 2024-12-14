package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

public interface AmericanDistributionsDataSupplier {

    ActualDistributions acquireDistributionHistoryForTicker(String productTicker);

}
