package com.priceguard.workflows.services;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PriceScraperService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USERNAME = "yashcc";
    private static final String PASSWORD = "Yash_carpenter00";

    public void run() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", "amazon_pricing");
        jsonObject.put("query", "B0BK1KS6ZD");
        jsonObject.put("user_agent_type", "desktop_chrome");
        jsonObject.put("parse", true);
        jsonObject.put("domain", "in");

        Authenticator authenticator = (route, response) -> {
            String credential = Credentials.basic(USERNAME, PASSWORD);

            return response.request()
                    .newBuilder()
                    .header(AUTHORIZATION_HEADER, credential)
                    .build();
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(authenticator)
                .build();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
        Request request = new Request.Builder()
                .url("https://realtime.oxylabs.io/v1/queries")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println(response.body().string());
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}