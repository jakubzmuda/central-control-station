package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakubzmuda.centralControlStation.investments.domain.core.Tuple;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.Currency;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRates;
import com.github.jakubzmuda.centralControlStation.investments.domain.currency.CurrencyRatesDataSupplier;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NbpCurrencyRatesDataSupplier implements CurrencyRatesDataSupplier {

    private OkHttpClient client;
    private ObjectMapper objectMapper;

    @Value("${application.nbp.url}")
    private String nbpUrl;

    @Value("${application.nbp.port}")
    private String nbpPort;

    public NbpCurrencyRatesDataSupplier(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public CurrencyRates getCurrencyRates() {
        Request request = new Request.Builder()
                .url(String.format("%s:%s/api/exchangerates/tables/A/", nbpUrl, nbpPort))
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            @SuppressWarnings("unchecked")
            List<TableJson> tableList = objectMapper.readValue(responseBody, new TypeReference<List<TableJson>>() {
            });
            return new CurrencyRates(tableList
                    .getFirst()
                    .rates
                    .stream()
                    .filter(json -> Currency.isSupported(json.code))
                    .collect(Collectors.toMap(
                            rate -> Tuple.of(Currency.of(rate.code), Currency.PLN),
                            rate -> rate.mid
                    )));
        } catch (IOException e) {
            throw new RuntimeException("Could not acquire currency rates.", e);
        }
    }
}

class TableJson {
    List<CurrencyRateJson> rates;

    private TableJson() {
        this(null);
    }

    TableJson(List<CurrencyRateJson> rates) {
        this.rates = rates;
    }
}

class CurrencyRateJson {
    String code;
    float mid;

    private CurrencyRateJson() {
    }

    CurrencyRateJson(String code, float mid) {
        this.code = code;
        this.mid = mid;
    }
}
