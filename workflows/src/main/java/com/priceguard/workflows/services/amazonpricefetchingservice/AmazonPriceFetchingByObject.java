package com.priceguard.workflows.services.amazonpricefetchingservice;

import com.priceguard.workflows.dto.AmazonApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmazonPriceFetchingByObject {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://real-time-amazon-data.p.rapidapi.com/product-details?asin={asin}&country=IN";
    private static final String RAPIDAPI_HOST = "real-time-amazon-data.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "38155b25ddmshb3888154327984fp19c2edjsn75e1ae08b291";

    public String getProductDetails(String asin) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-host", RAPIDAPI_HOST);
        headers.set("x-rapidapi-key", RAPIDAPI_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class, asin);

        return response.getBody();
    }

    public String getProductPrice(String asin) {
        String response = getProductDetails(asin);
        try {
            ObjectMapper mapper = new ObjectMapper();
            AmazonApiResponse apiResponse = mapper.readValue(response, AmazonApiResponse.class);
            return apiResponse.getData().getProductPrice();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

