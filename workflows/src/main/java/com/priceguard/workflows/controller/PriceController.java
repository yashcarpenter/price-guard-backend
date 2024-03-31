package com.priceguard.workflows.controller;

import com.priceguard.workflows.services.ProductPriceScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PriceController {
    @Autowired
    ProductPriceScraperService productPriceScraperService;

    @GetMapping("/getprice/{asin}")
    public Double getProductPrice(@PathVariable String asin){
        return productPriceScraperService.getPrice(asin);
    }
}
