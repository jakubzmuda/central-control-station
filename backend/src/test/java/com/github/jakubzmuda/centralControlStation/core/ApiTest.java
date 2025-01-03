package com.github.jakubzmuda.centralControlStation.core;

import com.github.jakubzmuda.centralControlStation.Application;
import com.github.jakubzmuda.centralControlStation.core.rest.Api;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.time.*;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = {Application.class, TestApplication.class}, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class ApiTest {

    @Autowired
    protected Api api;

    @Autowired
    protected Database database;

    @Autowired
    protected DistributionsHelper distributionsHelper;

    @Autowired
    protected CurrencyRatesDataProviderHelper currencyDataProviderHelper;

    @Value( "${application.nbp.port}" )
    private Integer nbpPort;

    protected WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        api.reset();
        distributionsHelper.reset();
        currencyDataProviderHelper.reset();
        database.reset();
        wireMockServer = new WireMockServer(nbpPort);
        wireMockServer.start();
        configureFor("localhost", nbpPort);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Bean
    Clock clock() {
        return Clock.fixed(Instant.ofEpochSecond(LocalDateTime.of(2024, 11, 20, 10, 0).toEpochSecond(ZoneOffset.UTC)), ZoneId.of("UTC"));
    }

}