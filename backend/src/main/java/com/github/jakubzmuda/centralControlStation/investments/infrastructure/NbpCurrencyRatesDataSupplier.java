package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRatesDataSupplier;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class NbpCurrencyRatesDataSupplier implements CurrencyRatesDataSupplier {

    private OkHttpClient client;
    private ObjectMapper objectMapper;
    private StubbedProductDistributions StubbedProductDistributions = new StubbedProductDistributions();

    @Value("${application.nbp.url}")
    private String nbpUrl;

    @Value("${application.nbp.port}")
    private String nbpPort;


    @Override
    public float getUsdPlnRate() {
        Request request = new Request.Builder()
                .url(String.format("%s:%s/api/exchangerates/tables/A/", nbpUrl, nbpPort))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            @SuppressWarnings("unchecked")
            List<TableJson> tableList = (List<TableJson>) objectMapper.readValue(responseBody, List.class);

            return tableList.getFirst().rates().stream()
                    .filter(rate -> rate.code().equals("USD"))
                    .findFirst()
                    .map(CurrencyRateJson::mid)
                    .orElseThrow(() -> new RuntimeException("Could not find USD rate."));
        } catch (IOException e) {
            throw new RuntimeException("Could not acquire currency rates.", e);
        }
    }
}

record TableJson(List<CurrencyRateJson> rates) {
}

record CurrencyRateJson(String code, float mid) {
}
