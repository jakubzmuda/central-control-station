package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.core.ApiTest;
import com.github.jakubzmuda.centralControlStation.core.TestUser;
import com.github.jakubzmuda.centralControlStation.core.rest.Response;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus.UNAUTHORIZED;
import static com.github.jakubzmuda.centralControlStation.core.rest.RestAssertions.assertThat;

public class PortfolioEndpointTest extends ApiTest {

    @Test
    public void shouldBeAbleToGetBothPortfoliosAsAuthenticatedUser() {
        givenPortfolio(TestUser.Max,
                new PortfolioEntry("msft", 10f)
        );
        givenPortfolio(TestUser.Charles,
                new PortfolioEntry("aapl", 20f),
                new PortfolioEntry("jpm", 5f)
        );

        Response response = api.whenAs(TestUser.Max).get("/api/portfolios");
        var responseBody = response.bodyAs(PortfoliosEndpoint.PortfoliosResponse.class);

        assertThat(responseBody.portfolios).hasSize(2);

        PortfoliosEndpoint.PortfolioJson maxPortfolio = responseBody.portfolios.get("max");
        assertThat(maxPortfolio.entries).hasSize(1);
        assertThat(maxPortfolio.entries.get(0).productTicker).isEqualTo("msft");
        assertThat(maxPortfolio.entries.get(0).amount).isEqualTo(10f);

        PortfoliosEndpoint.PortfolioJson charlesPortfolio = responseBody.portfolios.get("charles");
        assertThat(charlesPortfolio.entries).hasSize(2);
        assertThat(charlesPortfolio.entries.get(0).productTicker).isEqualTo("aapl");
        assertThat(charlesPortfolio.entries.get(0).amount).isEqualTo(20f);

        assertThat(charlesPortfolio.entries.get(1).productTicker).isEqualTo("jpm");
        assertThat(charlesPortfolio.entries.get(1).amount).isEqualTo(5f);
    }

    @Test
    public void shouldBeAbleToSavePortfolio() {
        Response response = api.whenAs(TestUser.Max).put("/api/portfolios/max", new PortfoliosEndpoint.PortfolioJson(List.of(
                new PortfoliosEndpoint.PortfolioEntryJson("msft", 10f),
                new PortfoliosEndpoint.PortfolioEntryJson("aapl", 2.5f)
        )));

        assertThat(response).hasStatus(OK);

        assertThat(database.count(Portfolio.class)).isEqualTo(1);

        Portfolio savedPortfolio = database.one(Portfolio.class);
        assertThat(savedPortfolio.userId()).isEqualTo(TestUser.Max.id());

        List<PortfolioEntry> portfolioEntries = savedPortfolio.entries();

        assertThat(portfolioEntries).hasSize(2);

        assertThat(portfolioEntries.getFirst().productTicker()).isEqualTo("msft");
        assertThat(portfolioEntries.getFirst().amount()).isEqualTo(10f);

        assertThat(portfolioEntries.getLast().productTicker()).isEqualTo("aapl");
        assertThat(portfolioEntries.getLast().amount()).isEqualTo(2.5f);
    }

    @Test
    public void shouldBeAbleToSavePortfolioForDifferentUser() {
        Response response = api.whenAs(TestUser.Max).put("/api/portfolios/charles", new PortfoliosEndpoint.PortfolioJson(List.of(
                new PortfoliosEndpoint.PortfolioEntryJson("msft", 10f),
                new PortfoliosEndpoint.PortfolioEntryJson("aapl", 2.5f)
        )));

        assertThat(response).hasStatus(OK);

        assertThat(database.count(Portfolio.class)).isEqualTo(1);

        Portfolio savedPortfolio = database.one(Portfolio.class);
        assertThat(savedPortfolio.userId()).isEqualTo(TestUser.Charles.id());

        List<PortfolioEntry> portfolioEntries = savedPortfolio.entries();

        assertThat(portfolioEntries).hasSize(2);

        assertThat(portfolioEntries.getFirst().productTicker()).isEqualTo("msft");
        assertThat(portfolioEntries.getFirst().amount()).isEqualTo(10f);

        assertThat(portfolioEntries.getLast().productTicker()).isEqualTo("aapl");
        assertThat(portfolioEntries.getLast().amount()).isEqualTo(2.5f);
    }

    @Test
    public void shouldBeAbleToUpdatePortfolio() {
        givenPortfolio(TestUser.Max,
                new PortfolioEntry("msft", 10f)
        );

        Response response = api.whenAs(TestUser.Max).put("/api/portfolios/max", new PortfoliosEndpoint.PortfolioJson(List.of(
                new PortfoliosEndpoint.PortfolioEntryJson("msft", 12f),
                new PortfoliosEndpoint.PortfolioEntryJson("aapl", 2.5f)
        )));

        assertThat(response).hasStatus(OK);

        assertThat(database.count(Portfolio.class)).isEqualTo(1);

        Portfolio savedPortfolio = database.one(Portfolio.class);
        assertThat(savedPortfolio.userId()).isEqualTo(TestUser.Max.id());

        List<PortfolioEntry> portfolioEntries = savedPortfolio.entries();

        assertThat(portfolioEntries).hasSize(2);

        assertThat(portfolioEntries.getFirst().productTicker()).isEqualTo("msft");
        assertThat(portfolioEntries.getFirst().amount()).isEqualTo(12f);

        assertThat(portfolioEntries.getLast().productTicker()).isEqualTo("aapl");
        assertThat(portfolioEntries.getLast().amount()).isEqualTo(2.5f);
    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticatedOnGet() {
        Response response = api.whenAnonymously().get("/api/portfolios");

        assertThat(response).hasStatus(UNAUTHORIZED);
    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticatedOnSave() {
        Response response = api.whenAnonymously().put("/api/portfolios/max", new PortfoliosEndpoint.PortfolioJson(List.of(
                new PortfoliosEndpoint.PortfolioEntryJson("msft", 12f),
                new PortfoliosEndpoint.PortfolioEntryJson("aapl", 2.5f)
        )));

        assertThat(response).hasStatus(UNAUTHORIZED);
    }


    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }
}
