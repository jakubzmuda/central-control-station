package com.github.jakubzmuda.centralControlStation.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthEndpoint {

    @GetMapping
    public String get() {
        return "ok";
    }

}