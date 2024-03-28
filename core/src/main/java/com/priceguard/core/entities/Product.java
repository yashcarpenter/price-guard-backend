package com.priceguard.core.entities;

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
    @Column(name = "url")
    private String url;

    @Column(name="min_price")
    private Double minPrice;

    @Column(name="last_price")
    private Double lastPrice;

    public Product(String url) {
        this.url = url;
    }
}
