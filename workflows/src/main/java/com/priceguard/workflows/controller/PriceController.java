package com.priceguard.workflows.controller;

import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.repository.ProductPriceRepository;
import com.priceguard.workflows.services.ProductPriceScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceController {
    @Autowired
    ProductPriceScraperService productPriceScraperService;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @GetMapping("/getprice/{asin}")
    public Double getProductPrice(@PathVariable String asin){
        return productPriceScraperService.getPrice(asin);
    }

    @PostMapping("getprices/{asin}")
    public List<ProductPrice> getPriceOfaProduct(@PathVariable String asin){
        return productPriceRepository.findByAsinAsin(asin);
    }
}
