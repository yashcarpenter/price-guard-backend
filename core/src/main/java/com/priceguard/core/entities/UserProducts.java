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
public class UserProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_url", referencedColumnName = "url")
    private Product productUrl;

    @Column (name = "product_name")
    private String productName;

    @Column(name="limit_price")
    private double limitPrice;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @JsonIgnore
    private User user;
}
