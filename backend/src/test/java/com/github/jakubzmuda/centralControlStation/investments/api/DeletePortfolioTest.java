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

public class DeletePortfolioTest extends ApiTest {

    @Test
    public void shouldBeAbleToDeletePortfolio() {
        givenPortfolio(TestUser.Max,
                new PortfolioEntry("msft", 10f)
        );

        Response response = api.whenAs(TestUser.Max).delete("/api/portfolios/max");

        assertThat(response).hasStatus(OK);

        assertThat(database.count(Portfolio.class)).isEqualTo(0);
    }

    @Test
    public void shouldBeAbleToDeleteForOthers() {
        givenPortfolio(TestUser.Max,
                new PortfolioEntry("msft", 10f)
        );

        Response response = api.whenAs(TestUser.Charles).delete("/api/portfolios/max");

        assertThat(response).hasStatus(OK);

        assertThat(database.count(Portfolio.class)).isEqualTo(0);
    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticated() {
        Response response = api.whenAnonymously().delete("/api/portfolios/max");

        assertThat(response).hasStatus(UNAUTHORIZED);
    }


    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }
}
