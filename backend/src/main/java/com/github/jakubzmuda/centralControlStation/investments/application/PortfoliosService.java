package com.github.jakubzmuda.centralControlStation.investments.application;

import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.Portfolio;
import com.github.jakubzmuda.centralControlStation.investments.domain.portfolio.PortfolioRepository;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PortfoliosService {

    private CurrentUser currentUser;
    private PortfolioRepository repository;

    public PortfoliosService(CurrentUser currentUser , PortfolioRepository repository) {
        this.currentUser = currentUser;
        this.repository = repository;
    }

    public List<Portfolio> getAll() {
        currentUser.getOrUnauthorized();
        return repository.findAll();
    }

    public Optional<Portfolio> getForUser(UserId userId) {
        return repository.findByUserId(userId);
    }
}
