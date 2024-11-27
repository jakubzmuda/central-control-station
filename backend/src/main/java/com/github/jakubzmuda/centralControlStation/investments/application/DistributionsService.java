package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.core.application.NotFoundException;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.*;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DistributionsService {

    private PortfoliosService portfoliosApplication;
    private DistributionsDataSupplier distributionsDataSupplier;
    private CurrentUser currentUser;

    public DistributionsService(
            PortfoliosService portfoliosApplication,
            DistributionsDataSupplier distributionsDataSupplier,
            CurrentUser currentUser) {
        this.portfoliosApplication = portfoliosApplication;
        this.distributionsDataSupplier = distributionsDataSupplier;
        this.currentUser = currentUser;
    }

    public DistributionsForecast forecast(UserId userId) {
        currentUser.getOrUnauthorized();

        Portfolio portfolio = portfoliosApplication
                .getForUser(userId)
                .orElseThrow(NotFoundException::new);

        ActualDistributions distributions = distributionsForPortfolio(portfolio);

        List<ForecastedDistribution> forecastedDistributions = portfolio.entries()
                .stream()
                .map(portfolioEntry -> {
                    ActualDistributions distributionsForProduct = distributions.forProduct(portfolioEntry.productTicker());

                    MonetaryValue lastDistributionAmount = distributionsForProduct
                            .last()
                            .orElseThrow(() -> new RuntimeException("No distributions found for portfolioEntry " + portfolioEntry.productTicker()))
                            .monetaryValue();

                    return distributionsForProduct.lastYear()
                            .distributionList()
                            .stream()
                            .map(distribution -> new ForecastedDistribution(
                                    portfolioEntry.productTicker(),
                                    lastDistributionAmount.multiply(portfolioEntry.amount()),
                                    Month.of(distribution.exDate())))
                            .toList();
                })
                .flatMap(List::stream)
                .toList();

        List<MonthlyForecast> monthlyForecasts = Arrays
                .stream(Month.values())
                .map(month -> new MonthlyForecast(
                        month,
                        forecastedDistributions.stream()
                                .filter(distribution -> distribution.month().equals(month))
                                .toList()))
                .collect(Collectors.toList());

        return new DistributionsForecast(new YearlyForecast(monthlyForecasts));
    }

    private ActualDistributions distributionsForPortfolio(Portfolio portfolio) {
        return portfolio
                .entries()
                .stream()
                .map(entry -> distributionsDataSupplier.acquireLastYearDistributions(entry.productTicker()))
                .reduce(ActualDistributions::addAll)
                .orElse(ActualDistributions.empty());
    }
}
