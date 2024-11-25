package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.application.PortfoliosService;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    @PutMapping("/{userId}")
    public void save(@PathVariable String userId, @RequestBody PortfolioJson request) {
        List<PortfolioEntry> entries = request.entries
                .stream()
                .map(entry -> new PortfolioEntry(entry.productTicker, entry.amount))
                .toList();
        application.save(new Portfolio(UserId.of(userId), entries));
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

        public PortfolioJson(List<PortfolioEntryJson> entries) {
            this.entries = entries;
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

        public PortfolioEntryJson(String productTicker, float amount) {
            this.productTicker = productTicker;
            this.amount = amount;
        }
    }
}