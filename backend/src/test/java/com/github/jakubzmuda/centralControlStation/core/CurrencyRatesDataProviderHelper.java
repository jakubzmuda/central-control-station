package com.github.jakubzmuda.centralControlStation.core;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@Component
public class CurrencyRatesDataProviderHelper {

    private float usdPlnRate = 0f;

    public CurrencyRatesDataProviderHelper withUsdPlnRate(float rate) {
        this.usdPlnRate = rate;
        return this;
    }

    public void stub() {
        String body = String.format("""
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
                      }
                    ]
                  }
                ]
                """, this.usdPlnRate);


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
