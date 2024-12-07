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

public class GetPortfolioTest extends ApiTest {

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
    public void shouldReturnUnauthorizedWhenNotAuthenticatedOnGet() {
        Response response = api.whenAnonymously().get("/api/portfolios");

        assertThat(response).hasStatus(UNAUTHORIZED);
    }

    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }
}
