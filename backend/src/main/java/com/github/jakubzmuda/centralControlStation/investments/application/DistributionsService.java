package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DistributionsService {

    private PortfolioRepository portfolioRepository;
    private DistributionDataAcquirementService dataAcquirementService;

    public DistributionsService(PortfolioRepository portfolioRepository, DistributionDataAcquirementService dataAcquirementService) {
        this.portfolioRepository = portfolioRepository;
        this.dataAcquirementService = dataAcquirementService;
    }

    public DistributionForecast forecast() {
        Portfolio portfolio = portfolioRepository.getPortfolio();

        portfolio.entries().forEach(entry -> dataAcquirementService.acquireYearlyForecastForProduct(entry.name()));


        return new DistributionForecast(new YearlyForecast(Map.of()));
    }
}
