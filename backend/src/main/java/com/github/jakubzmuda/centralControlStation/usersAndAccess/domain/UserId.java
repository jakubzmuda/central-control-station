package com.github.jakubzmuda.centralControlStation.usersAndAccess.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(String value) {

    public static UserId of(String id) {
        return new UserId(id);
    }

}
