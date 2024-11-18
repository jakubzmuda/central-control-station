package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.UserId;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.Distribution;
import com.github.jakubzmuda.centralControlStation.investments.domain.distributions.DistributionForecast;
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
        return new ForecastResponse(application.forecast(UserId.of("user")));
    }

    static class ForecastResponse {
        YearlyForecastJson yearlyForecast;

        private ForecastResponse() {
        }

        public ForecastResponse(DistributionForecast distributionForecast) {
            this.yearlyForecast = new YearlyForecastJson(distributionForecast.yearlyForecast());
        }

        public YearlyForecastJson yearlyForecast() {
            return yearlyForecast;
        }
    }

    static class YearlyForecastJson {
        LinkedHashMap<String, DistributionListJson> months;
        DistributionJson total;

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

        public DistributionJson total() {
            return total;
        }
    }

    static class DistributionListJson {
        List<DistributionJson> distributions;
        DistributionJson total;

        private DistributionListJson() {
        }

        public DistributionListJson(List<Distribution> distributions) {
            this.distributions = distributions.stream().map(DistributionJson::new).toList();
        }

        public List<DistributionJson> distributions() {
            return distributions;
        }

        public DistributionJson total() {
            return total;
        }
    }

    static class DistributionJson {
        String source;
        Map<String, Float> monetaryValue;

        private DistributionJson() {
        }

        public DistributionJson(Distribution distribution) {
            this.source = distribution.productTicker();
            this.monetaryValue = Map.of(distribution.monetaryValue().currency().toString(), distribution.monetaryValue().amount());
        }

        public String source() {
            return source;
        }

        public Map<String, Float> monetaryValue() {
            return monetaryValue;
        }
    }
}