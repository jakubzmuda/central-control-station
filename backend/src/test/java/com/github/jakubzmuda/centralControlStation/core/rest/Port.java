package com.github.jakubzmuda.centralControlStation.core.rest;

import org.springframework.core.env.Environment;

public class Port {

    private final Environment environment;

    public Port(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return environment.getProperty("local.server.port", Integer.class).toString();
    }
}