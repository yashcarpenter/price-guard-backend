package com.priceguard.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asin", referencedColumnName = "asin")
    private Product productAsin;

    @Column (name = "product_name")
    private String productName;

    @Column(name="limit_price")
    private double limitPrice;

    @Column
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @JsonIgnore
    private User user;
}
