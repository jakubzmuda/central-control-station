package com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest;

public class RestAssertions extends org.assertj.core.api.Assertions {

    public static HttpResponseAssert assertThat(Response actual) {
        return new HttpResponseAssert(actual);
    }
}