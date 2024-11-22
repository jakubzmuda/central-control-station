package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.core.ApiTest;
import com.github.jakubzmuda.centralControlStation.investments.core.Database;
import com.github.jakubzmuda.centralControlStation.investments.core.TestUser;
import com.github.jakubzmuda.centralControlStation.investments.core.rest.Response;
import com.github.jakubzmuda.centralControlStation.investments.core.rest.ResponseStatus;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.jakubzmuda.centralControlStation.investments.core.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.investments.core.rest.RestAssertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributionsEndpointTest extends ApiTest {

    @Autowired
    private Database database;

    @Test
    public void shouldRespondWithForecast() {
        givenPortfolio(
                TestUser.Max,
                new PortfolioEntry("aapl", 1.50f),
                new PortfolioEntry("jpm", 2.30f)
        );

        distributionsHelper
                .withProduct("aapl")
                .withDistribution(0.24f, LocalDate.parse("2024-02-09"))
                .withDistribution(0.25f, LocalDate.parse("2024-05-10"))
                .withDistribution(0.25f, LocalDate.parse("2024-08-12"))
                .withDistribution(0.25f, LocalDate.parse("2024-11-14"))
                .stub();

        distributionsHelper
                .withProduct("jpm")
                .withDistribution(0.25f, LocalDate.parse("2024-01-15"))
                .withDistribution(0.25f, LocalDate.parse("2024-04-15"))
                .withDistribution(0.25f, LocalDate.parse("2024-07-15"))
                .withDistribution(0.25f, LocalDate.parse("2024-10-15"))
                .stub();

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThereIsASingleDistributionInMonth(months, "january", "jpm", 0.575f);
    }

    @Test
    public void shouldCalculateTotalForMonth() {
        givenPortfolio(
                TestUser.Max,
                new PortfolioEntry("aapl", 1f),
                new PortfolioEntry("jpm", 2f)
        );

        distributionsHelper
                .withProduct("aapl")
                .withDistribution(0.24f, LocalDate.parse("2024-02-09"))
                .stub();

        distributionsHelper
                .withProduct("jpm")
                .withDistribution(0.25f, LocalDate.parse("2024-02-15"))
                .stub();

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast");
        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        assertThat(responseBody.yearlyForecast.months.get("february").total.get("USD")).isEqualTo(0.74f);
    }

    @Test
    public void shouldCalculateTotalForYear() {
        givenPortfolio(
                TestUser.Max,
                new PortfolioEntry("aapl", 1f),
                new PortfolioEntry("jpm", 2f)
        );

        distributionsHelper
                .withProduct("aapl")
                .withDistribution(0.24f, LocalDate.parse("2024-02-09"))
                .stub();

        distributionsHelper
                .withProduct("jpm")
                .withDistribution(0.25f, LocalDate.parse("2024-02-15"))
                .withDistribution(0.25f, LocalDate.parse("2024-05-15"))
                .stub();

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast");
        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        assertThat(responseBody.yearlyForecast.total.get("USD")).isEqualTo(1.24f);
    }

    @Test
    public void shouldShowTotalZeroWhenNoDistributions() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThat(responseBody.yearlyForecast.months.get("january").total.get("USD")).isEqualTo(0f);
        assertThat(responseBody.yearlyForecast.total.get("USD")).isEqualTo(0f);

    }

    @Test
    public void shouldSortMoths() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThat(months.sequencedKeySet()).containsExactly("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");

    }

    @Test
    public void shouldReturn404WhenNoPortfolio() {
        Response response = api.whenAs(TestUser.Charles).get("/api/distributions/forecast");

        assertThat(response).hasStatus(ResponseStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnUnauthorizedWhenCallingAnonymously() {
        Response response = api.whenAnonymously().get("/api/distributions/forecast");

        assertThat(response).hasStatus(ResponseStatus.UNAUTHORIZED);
    }

    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }

    private void assertThereIsASingleDistributionInMonth(Map<String, DistributionsEndpoint.DistributionListJson> months, String month, String productTicker, float usdAmount) {
        DistributionsEndpoint.DistributionListJson januaryDistributions = months.get(month);
        assertThat(januaryDistributions.distributions).hasSize(1);
        assertThat(januaryDistributions.distributions.getFirst().product).isEqualTo(productTicker);
        assertThat(januaryDistributions.distributions.getFirst().monetaryValue.get("USD")).isEqualTo(usdAmount);
    }

}