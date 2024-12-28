package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;


public interface EtfDistributionDataSupplier {

    ActualDistributions acquireDistributionHistoryForIsin(String productTicker, Currency currency);

}
