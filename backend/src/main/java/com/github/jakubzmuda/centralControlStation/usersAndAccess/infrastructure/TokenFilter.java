package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure;

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
        try {
            String authorizationToken = request.getHeader("Authorization");
//            Token token = Token.of(decodedToken.getUid());
//            CurrentUser currentUser = context.getBean(CurrentUser.class);
//            currentUser.authorize(UserId.of(token.value()));
        } catch (Exception ignored) {

        }
        filterChain.doFilter(request, response);
    }
}