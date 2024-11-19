package com.github.jakubzmuda.centralControlStation.investments.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@Component
public class DistributionsDataProviderHelper {

    private String productName;
    private List<DistributionRecord> distributions = new ArrayList<>();

    public DistributionsDataProviderHelper withProduct(String product) {
        this.productName = product;
        return this;
    }

    public DistributionsDataProviderHelper withDistribution(float amount, LocalDate exDate) {
        this.distributions.add(new DistributionRecord(amount, exDate));
        return this;
    }

    public void stub() {
        StringBuilder distributions = new StringBuilder();

        for(int i = 0; i < this.distributions.size(); i++) {
            distributions.append(distribution(this.distributions.get(i).amount(), this.distributions.get(i).exDate(), i < this.distributions.size() - 1));
        }

        String body = String.format(
                """
                        {
                          "data": [
                            %s
                          ]
                        }
                        """
                , distributions);
        
        WireMock.stubFor(get(String.format("/api/v3/symbols/%s/dividend_history?years=0", productName))
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));
    }

    public void reset() {
        this.productName = null;
        this.distributions = new ArrayList<>();
    }

    private String distribution(float amount, LocalDate exDate, boolean next) {
        return String.format(
                """
                        {
                              "id": "",
                              "type": "",
                              "attributes": {
                                "year": "2024",
                                "amount": "%s",
                                "ex_date": "%s",
                                "freq": "QUARTERLY",
                                "declare_date": "%s",
                                "pay_date": "%s",
                                "record_date": "%s",
                                "date": "%s",
                                "adjusted_amount": 0.24,
                                "split_adj_factor": 1.0,
                                "type": "Regular"
                              }
                        }%s
                        """, amount + "", exDate, exDate, exDate, exDate, exDate, next ? "," : "");
    }
}

record DistributionRecord(float amount, LocalDate exDate) {
}
