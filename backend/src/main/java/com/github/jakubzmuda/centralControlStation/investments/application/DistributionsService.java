package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.core.application.NotFoundException;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.Month;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.*;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DistributionsService {

    private PortfoliosService portfoliosApplication;
    private CurrenciesService currenciesApplication;
    private DistributionsDataSupplier distributionsDataSupplier;
    private CurrentUser currentUser;

    public DistributionsService(
            PortfoliosService portfoliosApplication,
            DistributionsDataSupplier distributionsDataSupplier,
            CurrenciesService currenciesApplication,
            CurrentUser currentUser) {
        this.portfoliosApplication = portfoliosApplication;
        this.distributionsDataSupplier = distributionsDataSupplier;
        this.currentUser = currentUser;
    }

    public DistributionsForecast forecast(UserId userId) {
        currentUser.getOrUnauthorized();

        float usdPlnRate = currenciesApplication.getUsdPlnRate();

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

        LinkedHashMap<Month, List<ForecastedDistribution>> distributionsPerMonth = Arrays.stream(Month.values())
                .collect(LinkedHashMap::new, (map, month) -> map.put(month, new ArrayList<>()), LinkedHashMap::putAll);


        for (ForecastedDistribution distribution : forecastedDistributions) {
            distributionsPerMonth.get(distribution.month()).add(distribution);
        }

        return new DistributionsForecast(new YearlyForecast(distributionsPerMonth));
    }

//    public Map<Currency, Float> calculateTotal(CurrencyRates currencyRates) {
//        Map<Currency, Float> result = new HashMap<>();
//
//        currencyRates.currencies().forEach(currency -> {
//            Map<Currency, Float> totalPerCurrency = calculateTotalPerCurrency();
//
//            float currencyNativeAmount = totalPerCurrency.get(currency);
//
//            float amount = currencyNativeAmount + currencyRates.currencies()
//                    .stream()
//                    .filter(c -> c != currency)
//                    .map(c -> currencyRates.rateFor(c, currency) * totalPerCurrency.get(c))
//                    .reduce(0f, Float::sum);
//
//            result.put(currency, amount);
//        });
//
//        return result;
//    }
//
//    private Map<Currency, Float> calculateTotalPerCurrency() {
//        return months.values().stream()
//                .map(month -> month.total)
//                .flatMap(map -> map.entrySet().stream())
//                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.collectingAndThen(
//                        Collectors.summingDouble(Map.Entry::getValue),
//                        Double::floatValue
//                )));
//    }

    private ActualDistributions distributionsForPortfolio(Portfolio portfolio) {
        return portfolio
                .entries()
                .stream()
                .map(entry -> distributionsDataSupplier.acquireLastYearDistributions(entry.productTicker()))
                .reduce(ActualDistributions::addAll)
                .orElse(ActualDistributions.empty());
    }

}
