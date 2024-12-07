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

public class SavePortfolioTest extends ApiTest {

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
