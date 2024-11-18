package com.github.jakubzmuda.centralControlStation.investments.core.rest;

import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseAssert extends AbstractAssert<HttpResponseAssert, Response> {
    HttpResponseAssert(Response actual) {
            super(actual, HttpResponseAssert.class);
    }

    public HttpResponseAssert hasStatus(ResponseStatus expectedStatus) {
        assertThat(ResponseStatus.valueOf(actual.statusCode())).isEqualTo(expectedStatus);
        return this;
    }

}