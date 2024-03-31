package com.priceguard.workflows.services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductPriceScraperService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USERNAME = "yashcccc";
    public static final String PASSWORD = "Oxylabs_yash00";

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

//public class ProductPriceScraperService {
//
//    public void run() {
//        String username = "yashcccc";
//        String password = "Oxylabs_yash00";
//        String url = "https://realtime.oxylabs.io/v1/queries";
//
//        // Create RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Create request headers with Basic Authentication
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBasicAuth(username, password);
//
//        // Create request body
//        String requestBody = "{\"source\": \"amazon_pricing\", \"query\": \"B0BK1KS6ZD\", \"parse\": true, \"domain\": \"in\"}";
//
//        // Create HTTP entity with headers and body
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//        // Make the POST request
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//        // Handle the response
//        if (response.getStatusCode() == HttpStatus.OK) {
//            String responseBody = response.getBody();
//            System.out.println("Response: " + responseBody);
//        } else {
//            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
//        }
//    }
//}

