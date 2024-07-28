package com.priceguard.workflows.services.amazonpricefetchingservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonApiService {

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

    public Double getProductPrice(String asin) {
        String response = getProductDetails(asin);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode productPriceNode = root.path("data").path("product_price");

            String priceString = productPriceNode.asText();
            System.out.println(priceString);
            Pattern pattern = Pattern.compile("[^\\d.]+");
            Matcher matcher = pattern.matcher(priceString);
            String numericPriceString = matcher.replaceAll("");

            return Double.parseDouble(numericPriceString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



