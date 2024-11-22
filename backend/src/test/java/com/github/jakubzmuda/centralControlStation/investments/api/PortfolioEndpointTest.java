package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.core.ApiTest;
import com.github.jakubzmuda.centralControlStation.core.TestUser;
import com.github.jakubzmuda.centralControlStation.core.rest.Response;
import com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus;
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
        givenPortfolio(TestUser.Max);
        givenPortfolio(TestUser.Charles);

        Response response = api.whenAs(TestUser.Max).get("/api/portfolios");

        assertThat(response).hasStatus(OK);

        var responseBody = response.bodyAs(PortfoliosEndpoint.PortfoliosResponse.class);

    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticated() {
        Response response = api.whenAnonymously().get("/api/portfolios");

        assertThat(response).hasStatus(UNAUTHORIZED);

    }

    private void givenPortfolio(TestUser user, PortfolioEntry... entries) {
        database.save(new Portfolio(user.id(), List.of(entries))
        );
    }
}
