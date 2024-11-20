package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.UserId;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.ActualDistribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionsForecast;
import com.github.jakubzmuda.centralControlStation.investments.application.DistributionsService;
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

        public YearlyForecastJson yearlyForecast() {
            return yearlyForecast;
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
                            entry -> new DistributionListJson(entry.getValue().distributionList()),
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));
        }

        public LinkedHashMap<String, DistributionListJson> months() {
            return months;
        }

        public Map<String, Float> total() {
            return total;
        }
    }

    static class DistributionListJson {
        List<DistributionJson> distributions;
        Map<String, Float> total;

        private DistributionListJson() {
        }

        public DistributionListJson(List<ActualDistribution> distributions) {
            this.distributions = distributions.stream().map(DistributionJson::new).toList();
        }

        public List<DistributionJson> distributions() {
            return distributions;
        }

        public Map<String, Float> total() {
            return total;
        }
    }

    static class DistributionJson {
        String product;
        Map<String, Float> monetaryValue;

        private DistributionJson() {
        }

        public DistributionJson(ActualDistribution distribution) {
            this.product = distribution.productTicker();
            this.monetaryValue = Map.of(distribution.monetaryValue().currency().toString(), distribution.monetaryValue().amount());
        }

        public String source() {
            return product;
        }

        public Map<String, Float> monetaryValue() {
            return monetaryValue;
        }
    }
}