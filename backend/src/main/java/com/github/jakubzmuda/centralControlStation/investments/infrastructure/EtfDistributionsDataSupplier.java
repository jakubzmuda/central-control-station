package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistributions;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.EtfDistributionDataSupplier;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class EtfDistributionsDataSupplier implements EtfDistributionDataSupplier {

    private OkHttpClient client;
    private ObjectMapper objectMapper;

    @Value("${application.atlas.url}")
    private String url;

    @Value("${application.atlas.port}")
    private String port;

    public EtfDistributionsDataSupplier(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public ActualDistributions acquireDistributionHistoryForIsin(String isin, Currency currency) {
        AtlasResponse distributionsResponse = acquireByIsin(isin);

        return new ActualDistributions(distributionsResponse.quotes
                .stream()
                .filter(entry -> entry.dividend != 0)
                .map(entry -> new ActualDistribution(
                        DistributionId.next(),
                        isin,
                        MonetaryValue.of(entry.dividend, currency))
                        .withExDate(LocalDate.parse(entry.date))
                ).toList());
    }

    private AtlasResponse acquireByIsin(String isin) {
        String url = String.format("%s:%s/api/v2/etf/dividend", this.url, port);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String json = String.format("{\"isin\":\"%s\"}", isin);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(json, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, AtlasResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Data acquirement for product " + isin + " failed", e);
        }

    }
}

class EtfIsinResolver {

    public Optional<String> fromPseudoTicker(String pseudoTicker) {
        return switch (pseudoTicker) {
            case "etf-sp500" -> Optional.of("IE0031442068 ");
            case "etf-europe" -> Optional.of("IE00B1YZSC51 ");
            case "etf-emerging-markets" -> Optional.of("IE00B0M63177 ");
            default -> Optional.empty();
        };
    }
}

class AtlasRequest {
    String isin;

    private AtlasRequest() {
    }

    public AtlasRequest(String isin) {
        this.isin = isin;
    }
}

class AtlasResponse {
    List<Quote> quotes;

    private AtlasResponse() {
    }
}

class Quote {
    @JsonProperty("date")
    String date;

    @JsonProperty("dividend")
    float dividend;


    private Quote() {
    }

}
