package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.ApiTest;
import com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.Response;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest.RestAssertions.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributionsEndpointTest extends ApiTest {

    @Test
    public void shouldRespondWithForecast() {
        WireMock.stubFor(get("/api/v3/symbols/aapl/dividend_history?years=0")
                .willReturn(aResponse()
                        .withBody(aaplResponse())
                        .withStatus(200)));

        WireMock.stubFor(get("/api/v3/symbols/msft/dividend_history?years=0")
                .willReturn(aResponse()
                        .withBody(msftResponse())
                        .withStatus(200)));

        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast().months;
        assertThat(months).hasSize(12);

    }

    private static String aaplResponse() {
        return """
                {
                  "data": [
                    {
                      "id": "8405100",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.24",
                        "ex_date": "2024-02-09",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-02-01",
                        "pay_date": "2024-02-15",
                        "record_date": "2024-02-12",
                        "date": "2024-02-09",
                        "adjusted_amount": 0.24,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "8405120",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.25",
                        "ex_date": "2024-05-10",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-05-02",
                        "pay_date": "2024-05-16",
                        "record_date": "2024-05-13",
                        "date": "2024-05-10",
                        "adjusted_amount": 0.25,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "8405140",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.25",
                        "ex_date": "2024-08-12",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-08-01",
                        "pay_date": "2024-08-15",
                        "record_date": "2024-08-12",
                        "date": "2024-08-12",
                        "adjusted_amount": 0.25,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "8405160",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.25",
                        "ex_date": "2024-11-08",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-10-31",
                        "pay_date": "2024-11-14",
                        "record_date": "2024-11-11",
                        "date": "2024-11-08",
                        "adjusted_amount": 0.25,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    }
                  ],
                  "meta": {
                    "current_year": 2024
                  }
                }
                """;
    }

    private static String msftResponse() {
        return """
                {
                  "data": [
                    {
                      "id": "5555220",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.75",
                        "ex_date": "2024-02-14",
                        "freq": "QUARTERLY",
                        "declare_date": "2023-11-28",
                        "pay_date": "2024-03-14",
                        "record_date": "2024-02-15",
                        "date": "2024-02-14",
                        "adjusted_amount": 0.75,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "5555240",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.75",
                        "ex_date": "2024-05-15",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-03-12",
                        "pay_date": "2024-06-13",
                        "record_date": "2024-05-16",
                        "date": "2024-05-15",
                        "adjusted_amount": 0.75,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "5555260",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.75",
                        "ex_date": "2024-08-15",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-06-12",
                        "pay_date": "2024-09-12",
                        "record_date": "2024-08-15",
                        "date": "2024-08-15",
                        "adjusted_amount": 0.75,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "5555280",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "0.83",
                        "ex_date": "2024-11-21",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-09-16",
                        "pay_date": "2024-12-12",
                        "record_date": "2024-11-21",
                        "date": "2024-11-21",
                        "adjusted_amount": 0.83,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    }
                  ],
                  "meta": {
                    "current_year": 2024
                  }
                }
                """;
    }

    @Test
    public void shouldSortMoths() {
        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast().months;

        assertThat(months.sequencedKeySet()).containsExactly("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");

    }

}