package com.priceguard.backend.repository;

import com.priceguard.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    public List<Product> findByUserEmail(String userEmail);

}

