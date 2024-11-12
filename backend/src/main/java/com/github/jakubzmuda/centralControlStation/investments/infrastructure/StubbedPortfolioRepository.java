package com.github.jakubzmuda.centralControlStation.investments.infrastructure;

import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioEntry;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StubbedPortfolioRepository implements PortfolioRepository {

    @Override
    public Portfolio getPortfolio() {
        return new Portfolio(List.of(
                new PortfolioEntry("aapl", 1.50f),
                new PortfolioEntry("msft", 2.30f)
        ));
    }
}
