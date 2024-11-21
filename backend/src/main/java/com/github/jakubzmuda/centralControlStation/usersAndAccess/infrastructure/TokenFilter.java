package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure;

import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationContext context;

    public TokenFilter() {

    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        CurrentUser currentUser = context.getBean(CurrentUser.class);

        try {
            String encodedToken = extractToken(request);
            Token token = Token.of(encodedToken);
            currentUser.authorize(UserId.of(token.getPayload().user()));
        } catch (Exception e) {
            currentUser.revoke();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.split(" ")[1];
    }

}