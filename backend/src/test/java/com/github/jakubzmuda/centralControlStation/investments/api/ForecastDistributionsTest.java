package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.core.ApiTest;
import com.github.jakubzmuda.centralControlStation.core.TestUser;
import com.github.jakubzmuda.centralControlStation.core.rest.Response;
import com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.core.rest.RestAssertions.assertThat;


public class ForecastDistributionsTest extends ApiTest {

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
                .stub();

        distributionsHelper
                .withProduct("jpm")
                .withDistribution(0.25f, LocalDate.parse("2024-01-15"))
                .withDistribution(0.25f, LocalDate.parse("2024-04-15"))
                .stub();

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThereIsASingleDistributionInMonth(months, "january", "jpm", 0.575f, "USD");
        assertThereIsASingleDistributionInMonth(months, "february", "aapl", 0.375f, "USD");
        assertThereIsASingleDistributionInMonth(months, "april", "jpm", 0.575f, "USD");
        assertThereIsASingleDistributionInMonth(months, "may", "aapl", 0.375f, "USD");
    }

    @Test
    public void shouldCalculateTotalForMonthForSameCurrency() {
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

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");
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

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");
        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        assertThat(responseBody.yearlyForecast.total.get("USD")).isEqualTo(1.24f);
    }

    @Test
    public void shouldShowTotalZeroWhenNoDistributions() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThat(responseBody.yearlyForecast.months.get("january").total).isNull();
        assertThat(responseBody.yearlyForecast.total).isNull();
    }

    @Test
    public void shouldSortMoths() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        LinkedHashMap<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThat(months.sequencedKeySet()).containsExactly("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");
    }

    @Test
    public void shouldWorkForSelectedPolishStocks() {
        givenPortfolio(
                TestUser.Max,
                new PortfolioEntry("domdev", 5f),
                new PortfolioEntry("archicom", 10f)
        );

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        assertThereIsASingleDistributionInMonth(months, "june", "domdev", 30f, "PLN");
        assertThereIsASingleDistributionInMonth(months, "july", "archicom", 14.099999f, "PLN");
        assertThereIsASingleDistributionInMonth(months, "october", "archicom", 14.099999f, "PLN");
        assertThereIsASingleDistributionInMonth(months, "december", "domdev", 30f, "PLN");
    }

    @Test
    public void shouldCalculateTotalPerCurrency() {
        givenPortfolio(
                TestUser.Max,
                new PortfolioEntry("domdev", 5f),
                new PortfolioEntry("aapl", 10f)
        );

        distributionsHelper
                .withProduct("aapl")
                .withDistribution(0.25f, LocalDate.parse("2024-06-09"))
                .stub();

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        Map<String, DistributionsEndpoint.DistributionListJson> months = responseBody.yearlyForecast.months;

        DistributionsEndpoint.DistributionListJson juneDistributions = months.get("june");
        DistributionsEndpoint.DistributionJson domdevDistribution = juneDistributions.distributions
                .stream().filter(d -> d.product.equals("domdev")).findFirst().get();
        assertThat(domdevDistribution).isNotNull();
        assertThat(domdevDistribution.monetaryValue.get("PLN")).isEqualTo(30f);

        DistributionsEndpoint.DistributionJson aaplDistirbution = juneDistributions.distributions
                .stream().filter(d -> d.product.equals("aapl")).findFirst().get();
        assertThat(aaplDistirbution).isNotNull();
        assertThat(aaplDistirbution.monetaryValue.get("USD")).isEqualTo(2.5f);

        assertThat(juneDistributions.total.get("PLN")).isEqualTo(30f);
        assertThat(juneDistributions.total.get("USD")).isEqualTo(2.5f);

        // It might look like it doesn't add up from previous assertions. That's because polish stock distributions are stubbed for now.
        assertThat(responseBody.yearlyForecast.total.get("PLN")).isEqualTo(60f);
        assertThat(responseBody.yearlyForecast.total.get("USD")).isEqualTo(2.5f);
    }

    @Test
    public void should_skip_portfolio_entry_without_distributions_history() {
        // TODO
    }


    @Test
    public void shouldBeAbleToForecastForAnyUserWhenAuthenticated() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Charles).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        assertThat(responseBody.yearlyForecast.months.keySet().size()).isEqualTo(12);
    }

    @Test
    public void shouldBeAbleToForecastEvenForEmptyPortfolio() {
        givenPortfolio(TestUser.Max);

        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(DistributionsEndpoint.ForecastResponse.class);

        assertThat(responseBody.yearlyForecast.months.keySet().size()).isEqualTo(12);
    }

    @Test
    public void shouldReturn404WhenNoPortfolio() {
        Response response = api.whenAs(TestUser.Max).get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(ResponseStatus.NOT_FOUND);
    }

    @Test
    public void shouldReturnUnauthorizedWhenCallingAnonymously() {
        Response response = api.whenAnonymously().get("/api/distributions/forecast?user=max");

        assertThat(response).hasStatus(ResponseStatus.UNAUTHORIZED);
    }

    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }

    private void assertThereIsASingleDistributionInMonth(Map<String, DistributionsEndpoint.DistributionListJson> months, String month, String productTicker, float amount, String currency) {
        DistributionsEndpoint.DistributionListJson distributions = months.get(month);
        assertThat(distributions.distributions).hasSize(1);
        assertThat(distributions.distributions.getFirst().product).isEqualTo(productTicker);
        assertThat(distributions.distributions.getFirst().monetaryValue.get(currency)).isEqualTo(amount);
    }
}