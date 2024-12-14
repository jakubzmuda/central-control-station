package com.github.jakubzmuda.centralControlStation.core;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@Component
public class DistributionsHelper {

    @Autowired
    private Database database;

    private String productTicker;
    private List<ActualDistribution> distributions = new ArrayList<>();

    public DistributionsHelper withProduct(String product) {
        this.productTicker = product;
        return this;
    }

    public DistributionsHelper withDistribution(MonetaryValue amount, LocalDate exDate) {
        this.distributions.add(new ActualDistribution(DistributionId.next(), productTicker, amount).withExDate(exDate));
        return this;
    }

    public void save() {
        this.distributions.forEach(distribution -> database.save(distribution));
        reset();
    }

    public void reset() {
        this.productTicker = null;
        this.distributions = new ArrayList<>();
    }
}