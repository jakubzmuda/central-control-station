package com.github.jakubzmuda.centralControlStation.investments.domain.portfolio;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends Repository<Portfolio, UserId> {

    Optional<Portfolio> findByUserId(UserId userId);

    List<Portfolio> findAll();

    void save(Portfolio portfolio);

    void deleteByUserId(UserId userId);
}
