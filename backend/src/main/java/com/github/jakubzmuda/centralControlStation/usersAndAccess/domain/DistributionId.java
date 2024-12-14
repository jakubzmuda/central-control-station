package com.github.jakubzmuda.centralControlStation.usersAndAccess.domain;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record DistributionId(String value) {

    public static DistributionId of(String id) {
        return new DistributionId(id);
    }

    public static DistributionId next() {
        return of("distribution-" + UUID.randomUUID());
    }

}
