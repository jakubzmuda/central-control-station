package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.core.ApiTest;
import com.github.jakubzmuda.centralControlStation.core.TestUser;
import com.github.jakubzmuda.centralControlStation.core.rest.Response;
import org.junit.jupiter.api.Test;

import static com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus.OK;
import static com.github.jakubzmuda.centralControlStation.core.rest.ResponseStatus.UNAUTHORIZED;
import static com.github.jakubzmuda.centralControlStation.core.rest.RestAssertions.assertThat;

public class CurrencyRatesEndpointTest extends ApiTest {

    @Test
    public void shouldGetRates() {
        currencyDataProviderHelper.withUsdPlnRate(4.5f).stub();

        Response response = api.whenAs(TestUser.Max).get("/api/currency-rates");

        assertThat(response).hasStatus(OK);

        CurrencyRatesEndpoint.CurrencyRatesResponse currencyRatesResponse = response.bodyAs(CurrencyRatesEndpoint.CurrencyRatesResponse.class);

        assertThat(currencyRatesResponse.rates.get("USD/PLN")).isEqualTo(4.5f);
    }

    @Test
    public void shouldReturnUnauthorizedWhenNotAuthenticatedOnGet() {
        currencyDataProviderHelper.withUsdPlnRate(4.5f).stub();

        Response response = api.whenAnonymously().get("/api/currency-rates");

        assertThat(response).hasStatus(UNAUTHORIZED);
    }

}
