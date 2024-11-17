package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.Distributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.YearlyForecast;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

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

        List<Distributions> distributionsPerPortfolioEntry = portfolio.entries().stream().map(entry -> dataAcquirementService.acquireLastYearDistributions(entry.name())).toList();

        LinkedHashMap<String, Distributions> distributionPerMonth = Arrays.stream(Month.values())
                .map(month -> month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase())
                .collect(Collectors.toMap(
                        monthName -> monthName,
                        monthName -> distributionsPerPortfolioEntry.stream()
                                .filter(distributions -> distributions.distributions().stream()
                                        .anyMatch(distribution -> distribution.exDate().getMonth()
                                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                                                .equalsIgnoreCase(monthName)))
                                .findFirst()
                                .orElseGet(Distributions::empty),
                        (oldValue, newValue) -> oldValue, // TODO merge function?
                        LinkedHashMap::new
                ));

        return new DistributionForecast(new YearlyForecast(distributionPerMonth));
    }
}
