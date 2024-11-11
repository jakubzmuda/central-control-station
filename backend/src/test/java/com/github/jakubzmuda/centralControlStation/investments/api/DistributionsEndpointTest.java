package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.rest.ApiTest;
import com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.rest.RestAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributionsEndpointTest extends ApiTest {

    @Test
    public void shouldRespondWithForecast() {
        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionList> months = responseBody.yearlyForecast().months;
        assertThat(months).hasSize(12);

        DistributionsEndpoint.DistributionList januaryDistributions = months.get("january");
        assertThat(januaryDistributions.distributions).hasSize(1);
    }
}