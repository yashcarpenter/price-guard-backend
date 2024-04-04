package com.priceguard.core.repository;
import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.entities.UserProducts;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    List<ProductPrice> findByAsinAsin(String asin);
}
