package com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.rest;

import com.github.jakubzmuda.centralControlStation.Application;
import com.github.jakubzmuda.centralControlStation.investments.api.infrastructure.any.TestApplication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = {Application.class, TestApplication.class}, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class ApiTest {

    @Autowired
    protected Api api;


    @BeforeEach
    public void setUp() {
        api.reset();
    }



}