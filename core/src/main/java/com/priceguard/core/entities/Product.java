package com.priceguard.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "asin", unique = true)
    private String asin;

    @Column(name="min_price")
    private Double minPrice;

    @Column(name="last_price")
    private Double lastPrice;

    @Column
    private LocalDateTime lastMinPriceAt;

    @ElementCollection
    @OneToMany(mappedBy = "asin", cascade = CascadeType.ALL)
    private List<ProductPrice> prices;

    public Product(String asin) {
        this.asin = asin;
    }

    public Product(String asin, double minPrice, double lastPrice, LocalDateTime lastMinPriceAt) {
        this.asin = asin;
        this.minPrice = minPrice;
        this.lastPrice = lastPrice;
        this.lastMinPriceAt = lastMinPriceAt;
    }
}
