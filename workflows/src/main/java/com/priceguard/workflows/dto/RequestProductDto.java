package com.priceguard.workflows.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductDto {
    private String productAsin;
    private String productName;
    private double limitPrice;
    private String userEmail;
}