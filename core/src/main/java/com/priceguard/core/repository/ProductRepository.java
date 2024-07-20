package com.priceguard.core.repository;

import com.priceguard.core.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}


