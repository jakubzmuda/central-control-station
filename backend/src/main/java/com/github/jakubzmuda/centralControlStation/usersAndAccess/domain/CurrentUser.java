package com.github.jakubzmuda.centralControlStation.usersAndAccess.domain;

import java.util.Optional;

public interface CurrentUser {
    void authorize(UserId userId);
    void revoke();
    Optional<UserId> tryGet();

    default UserId getOrUnauthorized() {
        return tryGet().orElseThrow(UnauthorizedException::new);
    }
}