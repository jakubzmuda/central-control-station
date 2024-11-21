package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.MonetaryValue;
import com.github.jakubzmuda.centralControlStation.investments.domain.usersAndAccess.UserId;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionsForecast;
import com.github.jakubzmuda.centralControlStation.investments.application.DistributionsService;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ForecastedDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.YearlyForecast;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ForecastResponse forecast() {
        return new ForecastResponse(application.forecast(UserId.of("test-user")));
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
                    .months()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> new DistributionListJson(entry.getValue()),
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));

            total = months.values().stream()
                    .map(month -> month.total)
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.collectingAndThen(
                            Collectors.summingDouble(Map.Entry::getValue),
                            Double::floatValue
                    )));
        }
    }

    static class DistributionListJson {
        List<DistributionJson> distributions;
        Map<String, Float> total;

        private DistributionListJson() {
        }

        public DistributionListJson(List<ForecastedDistribution> distributions) {
            this.distributions = distributions.stream().map(DistributionJson::new).toList();
            if (!distributions.isEmpty()) {
                this.total = Map.of(distributions.getFirst().monetaryValue().currency().toString(),
                        distributions
                                .stream()
                                .map(ForecastedDistribution::monetaryValue)
                                .map(MonetaryValue::amount)
                                .reduce(0f, Float::sum));
            } else {
                this.total = Map.of("USD", 0f);
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