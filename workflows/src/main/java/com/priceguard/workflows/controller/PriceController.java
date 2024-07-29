package com.priceguard.workflows.controller;

import com.priceguard.core.dao.ProductPriceDao;
import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.repository.ProductPriceRepository;
import com.priceguard.workflows.services.amazonpricefetchingservice.AmazonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
    private ProductPriceDao productPriceDao;

    @Autowired
    private AmazonApiService amazonApiService;

    @GetMapping("/getprice/{asin}")
    public Double getProductPrice(@PathVariable String asin){
        return amazonApiService.getProductPrice(asin);
    }

    @PostMapping("getprices/{asin}")
    public List<ProductPrice> getPriceOfaProduct(@PathVariable String asin){
        return productPriceDao.findByAsinAsin(asin);
    }

}
