package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

@Component
public class DistributionsService {

    private PortfolioRepository portfolioRepository;

    public DistributionsService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public DistributionForecast forecast() {
        Portfolio portfolio = portfolioRepository.getPortfolio();

        return null;
    }
}
