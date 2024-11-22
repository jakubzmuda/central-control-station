package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

public interface DistributionsDataSupplier {

    ActualDistributions acquireLastYearDistributions(String productTicker);

}
