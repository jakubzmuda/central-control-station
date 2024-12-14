package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface DistributionRepository extends Repository<ActualDistribution, DistributionId> {

    List<ActualDistribution> findByProductTicker(String productTicker);

    void deleteByProductTicker(String productTicker);

    void save(ActualDistribution distribution);
}
