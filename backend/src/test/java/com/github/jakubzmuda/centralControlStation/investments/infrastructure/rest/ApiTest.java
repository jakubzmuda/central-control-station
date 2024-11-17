package com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest;

import com.github.jakubzmuda.centralControlStation.Application;
import com.github.jakubzmuda.centralControlStation.investments.infrastructure.TestApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = {Application.class, TestApplication.class}, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class ApiTest {

    @Autowired
    protected Api api;

    @Value( "${application.seekingAlpha.port}" )
    private Integer seekingAlphaPort;

    protected WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        api.reset();
        wireMockServer = new WireMockServer(seekingAlphaPort);
        wireMockServer.start();
        configureFor("localhost", seekingAlphaPort);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

}