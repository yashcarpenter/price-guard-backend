package com.priceguard.workflows.services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductPriceScraperService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USERNAME = "yash_gs";
    public static final String PASSWORD = "Yash_sgsits00";

    public double getPrice(String asin) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source", "amazon_pricing");
        jsonObject.put("query", asin);
        jsonObject.put("user_agent_type", "desktop");
        jsonObject.put("parse", true);
        jsonObject.put("domain", "in");

        Authenticator authenticator = (route, response) -> {
            String credential = Credentials.basic(USERNAME, PASSWORD);

            return response
                    .request()
                    .newBuilder()
                    .header(AUTHORIZATION_HEADER, credential)
                    .build();
        };

        var client = new OkHttpClient.Builder()
                .authenticator(authenticator)
                .build();

        var mediaType = MediaType.parse("application/json; charset=utf-8");
        var body = RequestBody.create(jsonObject.toString(), mediaType);
        var request = new Request.Builder()
                .url("https://realtime.oxylabs.io/v1/queries")
                .post(body)
                .build();

        try (var response = client.newCall(request).execute()) {
            assert response.body() != null;
            return fetchPrice(response.body().string());
        } catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return 0;
    }

    public double fetchPrice(String data) {
        String jsonResponse = data;

        // Parse JSON
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        // Extract prices from the first result
        if (resultsArray.length() > 0) {
            JSONObject resultObject = resultsArray.getJSONObject(0);
            JSONObject contentObject = resultObject.getJSONObject("content");
            JSONArray pricingArray = contentObject.getJSONArray("pricing");

            if (pricingArray.length() > 0) {
                JSONObject priceObject = pricingArray.getJSONObject(0);
                double price = priceObject.getInt("price");
                return price;
            }
        }
        return -1;
    }
}

