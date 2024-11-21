package com.github.jakubzmuda.centralControlStation.usersAndAccess.domain;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized");
    }
}