package com.github.jakubzmuda.centralControlStation.investments.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DistributionDataAcquirementService {

    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public DistributionDataAcquirementService(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void acquireYearlyForecastForProduct(String productName) {
        // TODO map to domain
    }

    private void acquireDataFromExternalService(String productName) {
        Request request = new Request.Builder()
                .url(String.format("https://seekingalpha.com/api/v3/symbols/%s/dividend_history?years=1", productName))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                ProviderDistributionsResponse distributionsResponse = objectMapper.readValue(responseBody, ProviderDistributionsResponse.class);
            } else {
                throw new RuntimeException("Data acquirement for product " + productName + " was not successful");
            }
        } catch (IOException e) {
            throw new RuntimeException("Data acquirement for product " + productName + " failed", e);
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
