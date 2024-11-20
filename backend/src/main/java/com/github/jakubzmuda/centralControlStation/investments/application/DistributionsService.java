package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.core.application.NotFoundException;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.UserId;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionsForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ForecastedDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.YearlyForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class DistributionsService {

    private PortfolioRepository portfolioRepository;
    private DistributionDataAcquirementService dataAcquirementService;

    public DistributionsService(PortfolioRepository portfolioRepository, DistributionDataAcquirementService dataAcquirementService) {
        this.portfolioRepository = portfolioRepository;
        this.dataAcquirementService = dataAcquirementService;
    }

    public DistributionsForecast forecast(UserId userId) {
        Portfolio portfolio = portfolioRepository.getByUserId(userId).orElseThrow(NotFoundException::new);

        ActualDistributions distributions = distributionsForPortfolio(portfolio);

        List<ForecastedDistribution> forecastedDistributions = portfolio.entries()
                .stream()
                .map(portfolioEntry -> {
                    ActualDistributions distributionsForProduct = distributions.forProduct(portfolioEntry.name());

                    MonetaryValue lastDistributionAmount = distributionsForProduct
                            .last()
                            .orElseThrow(() -> new RuntimeException("No distributions found for portfolioEntry " + portfolioEntry.name()))
                            .monetaryValue();

                    return distributionsForProduct.lastYear()
                            .distributionList()
                            .stream()
                            .map(distribution -> new ForecastedDistribution(
                                    portfolioEntry.name(),
                                    lastDistributionAmount.multiply(portfolioEntry.amount()),
                                    Month.of(distribution.exDate())))
                            .toList();
                })
                .flatMap(List::stream)
                .toList();

        LinkedHashMap<String, List<ForecastedDistribution>> distributionsPerMonth = new LinkedHashMap<>();

        for (Month month : Month.values()) {
            distributionsPerMonth.put(month.getName(), new ArrayList<>());
        }

        for (ForecastedDistribution distribution : forecastedDistributions) {
            distributionsPerMonth.get(distribution.month().getName()).add(distribution);
        }

        return new DistributionsForecast(new YearlyForecast(distributionsPerMonth));
    }

    private ActualDistributions distributionsForPortfolio(Portfolio portfolio) {
        return portfolio
                .entries()
                .stream()
                .map(entry -> dataAcquirementService.acquireLastYearDistributions(entry.name()))
                .reduce(ActualDistributions::addAll)
                .orElse(ActualDistributions.empty());
    }

}
