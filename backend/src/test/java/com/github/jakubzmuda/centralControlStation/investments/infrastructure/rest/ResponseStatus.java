package com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest;

import static java.lang.String.format;

public enum ResponseStatus {

    OK(200),
    ACCEPTED(202),
    UNAUTHORIZED(401),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),
    NOT_FOUND(404),
    FORBIDDEN(403);

    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public static ResponseStatus valueOf(int status) {
        for (ResponseStatus responseStatus : values()) {
            if (responseStatus.is(status)) {
                return responseStatus;
            }
        }
        throw new IllegalArgumentException(format("Missing %s for code [%s]", ResponseStatus.class, status));
    }

    private boolean is(int status) {
        return this.code == status;
    }

    @Override
    public String toString() {
        return format("%s(%s)", name(), code);
    }
}