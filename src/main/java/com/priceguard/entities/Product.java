package com.priceguard.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "product_url")
    private String productUrl;

    @Column (name = "product_name")
    private String productName;

    @Column(name = "user_email")
    private User userEmail;

    @Column(name = "min_price")
    private double minPrice;

    @Column(name="limit_price")
    private double limitPrice;
}
