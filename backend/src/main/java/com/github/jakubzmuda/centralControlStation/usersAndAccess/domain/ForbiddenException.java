package com.github.jakubzmuda.centralControlStation.usersAndAccess.domain;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Forbidden");
    }
}