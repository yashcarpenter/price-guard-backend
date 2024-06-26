package com.priceguard.core.repository;

import com.priceguard.core.entities.UserProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProducts, Long> {

    public UserProducts findByUserEmailAndProductAsinAsin(String userEmail, String productUrl);

    public List<UserProducts> findByProductAsinAsin(String asin);

}

