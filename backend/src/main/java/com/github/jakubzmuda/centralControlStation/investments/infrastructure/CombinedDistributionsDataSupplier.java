package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionsDataSupplier;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CombinedDistributionsDataSupplier implements DistributionsDataSupplier {

    private OkHttpClient client;
    private ObjectMapper objectMapper;
    private StubbedProductDistributions StubbedProductDistributions = new StubbedProductDistributions();

    @Value("${application.seekingAlpha.url}")
    private String seekingAlphaUrl;

    @Value("${application.seekingAlpha.port}")
    private String seekingAlphaPort;

    public CombinedDistributionsDataSupplier(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public ActualDistributions acquireLastYearDistributions(String productTicker) {
        Optional<ActualDistributions> maybeStubbedDistributions = StubbedProductDistributions.get(productTicker);
        if (maybeStubbedDistributions.isPresent()) {
            return maybeStubbedDistributions.get();
        }

        ProviderDistributionsResponse distributionsResponse = acquireDataFromExternalService(productTicker);

        return new ActualDistributions(distributionsResponse.data
                .stream()
                .map(entry -> new ActualDistribution(
                        productTicker,
                        MonetaryValue.of(Currency.USD, Float.parseFloat(entry.attributes().amount())))
                        .withExDate(LocalDate.parse(entry.attributes().exDate()))
                        .withDeclareDate(LocalDate.parse(entry.attributes().declareDate()))
                        .withPayDate(LocalDate.parse(entry.attributes().payDate()))
                        .withRecordDate(LocalDate.parse(entry.attributes().recordDate()))
                ).toList());
    }

    private ProviderDistributionsResponse acquireDataFromExternalService(String productTicker) {
        String url = String.format("%s:%s/api/v3/symbols/%s/dividend_history?years=0", seekingAlphaUrl, seekingAlphaPort, productTicker);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, ProviderDistributionsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Data acquirement for product " + productTicker + " failed", e);
        }

    }
}

class ProviderDistributionsResponse {
    List<ProviderDistributionHistory> data;

    private ProviderDistributionsResponse() {
    }
}

class ProviderDistributionHistory {
    ProviderDistributionAttributes attributes;

    private ProviderDistributionHistory() {
    }

    public ProviderDistributionAttributes attributes() {
        return attributes;
    }
}

class ProviderDistributionAttributes {
    String amount;

    @JsonProperty("ex_date")
    String exDate;

    @JsonProperty("declare_date")
    String declareDate;

    @JsonProperty("pay_date")
    String payDate;

    @JsonProperty("record_date")
    String recordDate;

    public String amount() {
        return amount;
    }

    public String exDate() {
        return exDate;
    }

    public String declareDate() {
        return declareDate;
    }

    public String payDate() {
        return payDate;
    }

    public String recordDate() {
        return recordDate;
    }
}