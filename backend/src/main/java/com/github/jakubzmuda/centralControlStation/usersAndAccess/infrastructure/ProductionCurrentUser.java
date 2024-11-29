package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
@Profile("!test")
class ProductionCurrentUser implements CurrentUser {

    private UserId userId;

    @Override
    public void authorize(UserId userId) {
        this.userId = userId;
    }

    @Override
    public void revoke() {
        userId = null;
    }

    @Override
    public Optional<UserId> tryGet() {
        return Optional.of(userId);
    }
}