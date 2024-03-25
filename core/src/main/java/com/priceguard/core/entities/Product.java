package com.priceguard.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @Column(name = "product_url", unique = true)
    private String productUrl;

    @Column (name = "product_name")
    private String productName;

    @Column(name = "min_price")
    private double minPrice;

    @Column(name="limit_price")
    private double limitPrice;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @JsonIgnore
    private User user;
}
