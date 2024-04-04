package com.priceguard.workflows.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PriceCheckerService {
//    @Scheduled(fixedRate = 10000) // Execute every hour
    public void fetchPricesFromDatabase() {
        // Logic to fetch prices of all products from the database
        // Perform any necessary operations such as updating prices, sending notifications, etc.
        System.out.println("Fetching prices from the database...");
    }
}
