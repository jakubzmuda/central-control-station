package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortfoliosService {

    private CurrentUser currentUser;
    private PortfolioRepository repository;

    public PortfoliosService(CurrentUser currentUser , PortfolioRepository repository) {
        this.currentUser = currentUser;
        this.repository = repository;
    }

    public List<String> getPortfolios() {
        currentUser.getOrUnauthorized();
        return List.of("kroker");
    }
}
