package com.github.jakubzmuda.centralControlStation.core.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakubzmuda.centralControlStation.core.TestUser;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.CurrentUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.Util;

import java.io.IOException;

public class Api {

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    private final Port port;
    private final CurrentUser currentUser;

    public Api(Port port, CurrentUser currentUser) {
        this.port = port;
        this.currentUser = currentUser;
    }

    public void reset() {
        currentUser.revoke();
    }

    public Client whenAnonymously() {
        return new Client("http://localhost:" + port, client);
    }

    public Client whenAs(TestUser user) {
        currentUser.authorize(user.id());
        return new Client("http://localhost:" + port, client);
    }

    public static class Client {

        private final ObjectMapper objectMapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        private final String host;
        private final OkHttpClient client;

        private Client(String host, OkHttpClient client) {
            this.host = host;
            this.client = client;
        }

        public Response get(String url) {
            Request.Builder request = prepare(url)
                    .get();
            return execute(request);
        }

        public Response put(String url, Object json) {
            Request.Builder request = prepare(url)
                    .put(jsonBody(json));

            return execute(request);
        }

        public Response put(String url) {
            Request.Builder request = prepare(url)
                    .put(Util.EMPTY_REQUEST);
            return execute(request);
        }

        private Request.Builder prepare(String url) {
            return new Request.Builder()
                    .url(this.host + url);
        }

        private String toJson(Object json) {
            try {
                return objectMapper.writeValueAsString(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        public Response post(String url, Object json) {
            Request.Builder request = prepare(url)
                    .post(jsonBody(json));
            return execute(request);
        }

        public Response post(String url) {
            Request.Builder request = prepare(url)
                    .post(Util.EMPTY_REQUEST);
            return execute(request);
        }

        public Response delete(String url) {
            Request.Builder request = prepare(url)
                    .delete();
            return execute(request);
        }

        public Response delete(String url, Object json) {
            Request.Builder request = prepare(url)
                    .delete(jsonBody(json));
            return execute(request);
        }

        private RequestBody jsonBody(Object json) {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            return RequestBody.create(JSON, toJson(json));
        }

        private Response execute(Request.Builder request) {
            try {
                okhttp3.Response response = client.newCall(request.build()).execute();
                return new Response(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}