package com.github.jakubzmuda.centralControlStation.investments.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DistributionsEndpoint {

    @GetMapping("/api/distributions/forecast")
    public ForecastResponse forecast() {

        Map<String, DistributionList> months = new HashMap<>();
        months.put("january", new DistributionList(List.of(new Distribution("aapl", Map.of("USD", 10f)))));
        months.put("february", new DistributionList());
        months.put("march", new DistributionList());
        months.put("april", new DistributionList());
        months.put("may", new DistributionList());
        months.put("june", new DistributionList());
        months.put("july", new DistributionList());
        months.put("august", new DistributionList());
        months.put("september", new DistributionList());
        months.put("october", new DistributionList());
        months.put("november", new DistributionList());
        months.put("december", new DistributionList());

        return new ForecastResponse(new YearlyForecast(months));
    }

    static class ForecastResponse {
        YearlyForecast yearlyForecast;

        private ForecastResponse() {
        }

        public ForecastResponse(YearlyForecast yearlyForecast) {
            this.yearlyForecast = yearlyForecast;
        }

        public YearlyForecast yearlyForecast() {
            return yearlyForecast;
        }
    }

    static class YearlyForecast {
        Map<String, DistributionList> months;
        Distribution total;

        private YearlyForecast() {
        }

        public YearlyForecast(Map<String, DistributionList> months) {
            this.months = months;
        }
    }

    static class DistributionList {
        List<Distribution> distributions;
        Distribution total;

        private DistributionList() {
        }

        public DistributionList(List<Distribution> distributions) {
            this.distributions = distributions;
        }
    }

    static class Distribution {
        String source;
        Map<String, Float> monetaryValue;

        private Distribution() {
        }

        public Distribution(String source, Map<String, Float> monetaryValue) {
            this.source = source;
            this.monetaryValue = monetaryValue;
        }
    }
}