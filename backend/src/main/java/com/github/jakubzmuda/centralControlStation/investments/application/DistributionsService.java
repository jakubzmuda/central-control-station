package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.Distributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.YearlyForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class DistributionsService {

    private PortfolioRepository portfolioRepository;
    private DistributionDataAcquirementService dataAcquirementService;

    public DistributionsService(PortfolioRepository portfolioRepository, DistributionDataAcquirementService dataAcquirementService) {
        this.portfolioRepository = portfolioRepository;
        this.dataAcquirementService = dataAcquirementService;
    }

    // TODO iść przez portfolio entries i akumulować w miesiącach
    public DistributionForecast forecast() {
        Portfolio portfolio = portfolioRepository.getPortfolio();

        Distributions distributions = distributionsForPortfolio(portfolio);

        LinkedHashMap<String, Distributions> distributionPerMonth = monthNames()
                .stream()
                .collect(Collectors.toMap(
                        monthName -> monthName,
                        distributions::distributionsInMonth,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        return new DistributionForecast(new YearlyForecast(distributionPerMonth));
    }

    private List<String> monthNames() {
        return Arrays.stream(Month.values())
                .map(month -> month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase()).toList();
    }

    private Distributions distributionsForPortfolio(Portfolio portfolio) {
        return portfolio
                .entries()
                .stream()
                .map(entry -> dataAcquirementService.acquireLastYearDistributions(entry.name()))
                .reduce(Distributions::addAll)
                .orElse(Distributions.empty());
    }

}
