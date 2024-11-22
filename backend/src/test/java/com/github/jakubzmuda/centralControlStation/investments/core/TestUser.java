package com.github.jakubzmuda.centralControlStation.investments.core;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;

public enum TestUser {
    Max(UserId.of("max")),
    Charles(UserId.of("charles"));

    private final UserId id;

    TestUser(UserId id) {
        this.id = id;
    }

    public UserId id() {
        return this.id;
    }
}