package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import com.github.jakubzmuda.centralControlStation.investments.domain.core.UserId;
import org.springframework.data.repository.Repository;

public interface PortfolioRepository extends Repository<Portfolio, UserId> {

    Portfolio getByUserId(UserId userId);

    void save(Portfolio portfolio);
}
