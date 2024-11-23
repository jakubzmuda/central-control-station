package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.application.PortfoliosService;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfoliosEndpoint {

    private PortfoliosService application;

    public PortfoliosEndpoint(PortfoliosService application) {
        this.application = application;
    }

    @GetMapping
    public PortfoliosResponse get() {
        return new PortfoliosResponse(application.getAll());
    }

    static class PortfoliosResponse {

        Map<String, PortfolioJson> portfolios;

        private PortfoliosResponse() {
        }

        public PortfoliosResponse(List<Portfolio> all) {
            portfolios = all.stream()
                    .collect(HashMap::new, (map, portfolio) -> map.put(portfolio.userId().value(), new PortfolioJson(portfolio)), Map::putAll);
        }
    }

    static class PortfolioJson {
        List<PortfolioEntryJson> entries;

        private PortfolioJson() {
        }

        public PortfolioJson(Portfolio portfolio) {
            entries = portfolio.entries().stream()
                    .map(PortfolioEntryJson::new)
                    .toList();
        }
    }

    static class PortfolioEntryJson {
        String productTicker;
        float amount;

        private PortfolioEntryJson() {
        }

        public PortfolioEntryJson(PortfolioEntry entry) {
            productTicker = entry.productTicker();
            amount = entry.amount();
        }
    }
}