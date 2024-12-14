package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.AmericanDistributionsDataSupplier;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionRepository;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DistributionsDataFillingService {

    private static final Logger logger = LoggerFactory.getLogger(DistributionsDataFillingService.class);


    private PortfolioRepository portfolioRepository;
    private AmericanDistributionsDataSupplier americanDistributionsDataSupplier;
    private DistributionRepository distributionRepository;

    public DistributionsDataFillingService(PortfolioRepository portfolioRepository, AmericanDistributionsDataSupplier americanDistributionsDataSupplier, DistributionRepository distributionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.americanDistributionsDataSupplier = americanDistributionsDataSupplier;
        this.distributionRepository = distributionRepository;
    }

    @Transactional
    public void fillBasedOnPortfoliosEntries() {
        List<String> productTickersFromPortfolios = portfolioRepository
                .findAll()
                .stream()
                .map(Portfolio::entries)
                .flatMap(List::stream)
                .map(PortfolioEntry::productTicker).toList();

        productTickersFromPortfolios.forEach(this::fillForProductTicker);
    }

    private void fillForProductTicker(String productTicker) {
        logger.info("Attempting to fill distributions for product '{}'", productTicker);
        try {
            Optional<ActualDistributions> maybeStubbedDistributions = new StubbedProductDistributions().get(productTicker);
            if (maybeStubbedDistributions.isPresent()) {
                updateDistributions(maybeStubbedDistributions.get());
                logger.info("Persisted distributions for product '{}' from stubbed history", productTicker);
                return;
            }
            updateDistributions(americanDistributionsDataSupplier.acquireDistributionHistoryForTicker(productTicker));
            logger.info("Persisted distributions for product '{}' from external provider", productTicker);
        } catch (Exception e) {
            logger.error("Failed to update distributions for product '{}'", productTicker);
            throw e;
        }
    }

    private void updateDistributions(ActualDistributions actualDistributions) {
        distributionRepository.deleteByProductTicker(actualDistributions.productTicker());
        actualDistributions.distributionList().forEach(distribution -> distributionRepository.save(distribution));
    }
}

class StubbedProductDistributions {

    public Optional<ActualDistributions> get(String productTicker) {
        return switch (productTicker) {
            case "domdev" -> Optional.of(domdevDistributions());
            case "archicom" -> Optional.of(archicomDistributions());
            default -> Optional.empty();
        };
    }

    private ActualDistributions archicomDistributions() {
        return new ActualDistributions(List.of(
                new ActualDistribution(DistributionId.next(), "archicom", MonetaryValue.of(Currency.PLN, 1.41f))
                        .withExDate(LocalDate.parse("2024-10-29"))
                        .withPayDate(LocalDate.parse("2024-11-07")),
                new ActualDistribution(DistributionId.next(), "archicom", MonetaryValue.of(Currency.PLN, 0.64f))
                        .withExDate(LocalDate.parse("2024-07-09"))
                        .withPayDate(LocalDate.parse("2024-07-25"))
        ));
    }

    private ActualDistributions domdevDistributions() {
        return new ActualDistributions(List.of(
                new ActualDistribution(DistributionId.next(), "domdev", MonetaryValue.of(Currency.PLN, 6f))
                        .withExDate(LocalDate.parse("2024-12-10"))
                        .withPayDate(LocalDate.parse("2024-12-18")),
                new ActualDistribution(DistributionId.next(), "domdev", MonetaryValue.of(Currency.PLN, 6.5f))
                        .withExDate(LocalDate.parse("2024-06-24"))
                        .withPayDate(LocalDate.parse("2024-07-04"))
        ));
    }
}
