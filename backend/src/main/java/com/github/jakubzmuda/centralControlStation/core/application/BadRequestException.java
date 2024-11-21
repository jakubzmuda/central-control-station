package com.github.jakubzmuda.centralControlStation.core.application;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
