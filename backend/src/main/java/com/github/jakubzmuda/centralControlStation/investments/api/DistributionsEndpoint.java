package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MultiCurrencyMonetaryValue;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionsForecast;
import com.github.jakubzmuda.centralControlStation.investments.application.DistributionsService;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ForecastedDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.YearlyForecast;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DistributionsEndpoint {

    private DistributionsService application;

    public DistributionsEndpoint(DistributionsService application) {
        this.application = application;
    }

    @GetMapping("/api/distributions/forecast")
    public ForecastResponse forecast(@RequestParam("user") String userId) {
        return new ForecastResponse(application.forecast(UserId.of(userId)));
    }

    static class ForecastResponse {
        YearlyForecastJson yearlyForecast;

        private ForecastResponse() {
        }

        public ForecastResponse(DistributionsForecast distributionsForecast) {
            this.yearlyForecast = new YearlyForecastJson(distributionsForecast.yearlyForecast());
        }
    }

    static class YearlyForecastJson {
        LinkedHashMap<String, DistributionListJson> months;
        Map<String, Float> total;

        private YearlyForecastJson() {
        }

        public YearlyForecastJson(YearlyForecast yearlyForecast) {
            months = yearlyForecast
                    .monthlyForecasts()
                    .stream()
                    .collect(Collectors.toMap(
                            monthlyForecast -> monthlyForecast.month().getName(),
                            entry -> new DistributionListJson(entry.forecastedDistributions(), entry.totalForEachCurrency().orElse(null)),
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));

            if (yearlyForecast.total().isPresent()) {
                total = yearlyForecast
                        .total()
                        .get()
                        .asMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(key -> key.getKey().symbol(), Map.Entry::getValue));
            }
        }
    }

    static class DistributionListJson {
        List<DistributionJson> distributions;
        Map<String, Float> total;

        private DistributionListJson() {
        }

        public DistributionListJson(List<ForecastedDistribution> distributions, MultiCurrencyMonetaryValue total) {
            this.distributions = distributions
                    .stream()
                    .map(DistributionJson::new)
                    .toList();

            if (total != null) {
                this.total = total
                        .asMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(key -> key.getKey().symbol(), Map.Entry::getValue));
            }
        }
    }

    static class DistributionJson {
        String product;
        Map<String, Float> monetaryValue;

        private DistributionJson() {
        }

        public DistributionJson(ForecastedDistribution distribution) {
            this.product = distribution.productName();
            this.monetaryValue = Map.of(distribution.monetaryValue().currency().toString(), distribution.monetaryValue().amount());
        }
    }
}