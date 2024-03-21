package com.priceguard.core.repository;

import com.priceguard.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    public List<Product> findByUserEmail(String userEmail);

    public Product findByUserEmailAndProductUrl(String userEmail,String productUrl);

}

