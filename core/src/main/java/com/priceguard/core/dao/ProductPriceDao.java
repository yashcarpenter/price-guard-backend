package com.priceguard.core.dao;

import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductPriceDao {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    public List<ProductPrice> findByAsinAsin(String asin){
        return productPriceRepository.findByAsinAsin(asin);
    }

    public ProductPrice save(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }
}
