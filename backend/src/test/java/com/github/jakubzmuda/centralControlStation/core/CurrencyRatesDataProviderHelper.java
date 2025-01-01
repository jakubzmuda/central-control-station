package com.github.jakubzmuda.centralControlStation.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@Component
public class CurrencyRatesDataProviderHelper {

    private float usdPlnRate = 0f;
    private float eurPlnRate = 0f;

    public CurrencyRatesDataProviderHelper withUsdPlnRate(float rate) {
        this.usdPlnRate = rate;
        return this;
    }

    public CurrencyRatesDataProviderHelper withEurPlnRate(float rate) {
        this.eurPlnRate = rate;
        return this;
    }

    public void stub() {
        String body = String.format(Locale.US, """
                [
                  {
                    "table": "A",
                    "no": "227/A/NBP/2024",
                    "effectiveDate": "2024-11-22",
                    "rates": [
                      {
                        "currency": "dolar amerykański",
                        "code": "USD",
                        "mid": %f
                      },
                      {
                        "currency": "Euro",
                        "code": "EUR",
                        "mid": %f
                      }
                    ]
                  }
                ]
                """, this.usdPlnRate, this.eurPlnRate);


        WireMock.stubFor(get("/api/exchangerates/tables/A/")
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));

        reset();
    }

    public void reset() {
        this.usdPlnRate = 0f;
    }
}
