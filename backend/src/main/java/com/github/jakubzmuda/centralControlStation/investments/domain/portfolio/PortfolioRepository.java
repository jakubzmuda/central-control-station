package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import com.github.jakubzmuda.centralControlStation.investments.domain.usersAndAccess.UserId;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PortfolioRepository extends Repository<Portfolio, UserId> {

    Optional<Portfolio> getByUserId(UserId userId);

    void save(Portfolio portfolio);
}
