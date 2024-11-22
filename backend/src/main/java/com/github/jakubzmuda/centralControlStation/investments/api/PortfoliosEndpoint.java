package com.github.jakubzmuda.centralControlStation.investments.api;

import com.github.jakubzmuda.centralControlStation.investments.application.PortfoliosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfoliosEndpoint {

    private PortfoliosService application;

    public PortfoliosEndpoint(PortfoliosService application) {
        this.application = application;
    }

    @GetMapping
    public PortfoliosResponse get() {
        return new PortfoliosResponse(application.getPortfolios());
    }

    static class PortfoliosResponse {

        List<String> portfolios;

        private PortfoliosResponse() {
        }

        public PortfoliosResponse(List<String> portfolios) {
            this.portfolios = portfolios;
        }
    }
}