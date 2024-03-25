package com.priceguard.workflows.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String url;
    private String productName;
    private double limitPrice;
    private String userEmail;
}