package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("test")
class TestCurrentUser implements CurrentUser {

    private UserId userId;

    @Override
    public void authorize(UserId userId) { this.userId = userId; }

    @Override
    public void revoke() {
        userId = null;
    }

    @Override
    public Optional<UserId> tryGet() {
        return Optional.ofNullable(userId);
    }
}