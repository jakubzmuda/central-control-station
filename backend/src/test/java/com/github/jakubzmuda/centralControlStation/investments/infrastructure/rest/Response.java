package com.github.jakubzmuda.centralControlStation.investments.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class Response {

    private static final ObjectMapper jacksonMapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC))
            .setDateFormat(new StdDateFormat())
            .registerModule(new JavaTimeModule());

    private final okhttp3.Response response;

    Response(okhttp3.Response response) {
        this.response = response;
    }


    public int statusCode() {
        return response.code();
    }

    public <T> T bodyAs(Class<T> json) {
        try {
            return jacksonMapper.readValue(response.body().string(), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String url() {
        return response.networkResponse().request().url().toString();
    }
}