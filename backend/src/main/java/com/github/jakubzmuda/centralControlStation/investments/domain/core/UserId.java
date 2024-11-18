package com.github.jakubzmuda.centralControlStation.investments.domain.core;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(String value) {

    public static UserId of(String id) {
        return new UserId(id);
    }

}
