package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.github.jakubzmuda.centralControlStation.investments.application.DistributionsDataFillingService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("distributions-data-filling")
public class DistributionsDataFillingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DistributionsDataFillingConfiguration.class);

    @Autowired
    private DistributionsDataFillingService dataFillingService;

    @PostConstruct
    private void fillData() {
        logger.info("Starting distributions data filling process.");

        dataFillingService.fillBasedOnPortfoliosEntries();

        logger.info("Distributions data for existing portfolios persisted successfully.");
    }

}
