package com.github.jakubzmuda.centralControlStation.investments.domain.distributions;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.DistributionId;
import org.springframework.data.repository.Repository;

public interface DistributionRepository extends Repository<ActualDistribution, DistributionId> {

    void deleteByProductTicker(String productTicker);

    void save(ActualDistribution distribution);
}
