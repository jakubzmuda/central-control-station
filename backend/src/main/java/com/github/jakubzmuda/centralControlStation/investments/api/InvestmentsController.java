package com.github.jakubzmuda.centralControlStation.investments.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvestmentsController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}