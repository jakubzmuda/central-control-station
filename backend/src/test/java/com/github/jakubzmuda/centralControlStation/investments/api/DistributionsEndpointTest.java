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

// TODO test util that generates stub response with api like withProduct("aapl").amount(0.25f).onMonths("january", "april)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributionsEndpointTest extends ApiTest {

    @Test
    public void shouldRespondWithForecast() {
        stubDistributionsResponse("aapl", aaplResponse());
        stubDistributionsResponse("jpm", jpmResponse());

        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast().months;
        assertThat(months).hasSize(12);

        DistributionsEndpoint.DistributionListJson januaryDistributions = months.get("january");
        assertThat(januaryDistributions.distributions).hasSize(1);
        assertThat(januaryDistributions.distributions.getFirst().source).isEqualTo("jpm");
        assertThat(januaryDistributions.distributions.getFirst().monetaryValue.get("USD")).isEqualTo(2.875f);
        assertThat(januaryDistributions.total.source).isEqualTo("total");
        assertThat(januaryDistributions.total.monetaryValue.get("USD")).isEqualTo(2.875f);
    }

    private static void stubDistributionsResponse(String productName, String body) {
        WireMock.stubFor(get(String.format("/api/v3/symbols/%s/dividend_history?years=0", productName))
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));
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

    private static String jpmResponse() {
        return """
                {
                  "data": [
                    {
                      "id": "9980440",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "1.05",
                        "ex_date": "2024-01-04",
                        "freq": "QUARTERLY",
                        "declare_date": "2023-12-12",
                        "pay_date": "2024-01-31",
                        "record_date": "2024-01-05",
                        "date": "2024-01-04",
                        "adjusted_amount": 1.05,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "9980460",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "1.15",
                        "ex_date": "2024-04-04",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-03-19",
                        "pay_date": "2024-04-30",
                        "record_date": "2024-04-05",
                        "date": "2024-04-04",
                        "adjusted_amount": 1.15,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "9980480",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "1.15",
                        "ex_date": "2024-07-05",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-05-20",
                        "pay_date": "2024-07-31",
                        "record_date": "2024-07-05",
                        "date": "2024-07-05",
                        "adjusted_amount": 1.15,
                        "split_adj_factor": 1.0,
                        "type": "Regular"
                      }
                    },
                    {
                      "id": "9980500",
                      "type": "dividend_history",
                      "attributes": {
                        "year": 2024,
                        "amount": "1.25",
                        "ex_date": "2024-10-04",
                        "freq": "QUARTERLY",
                        "declare_date": "2024-09-17",
                        "pay_date": "2024-10-31",
                        "record_date": "2024-10-04",
                        "date": "2024-10-04",
                        "adjusted_amount": 1.25,
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
        stubDistributionsResponse("aapl", aaplResponse());
        stubDistributionsResponse("jpm", jpmResponse());

        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast().months;

        assertThat(months.sequencedKeySet()).containsExactly("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");

    }

}