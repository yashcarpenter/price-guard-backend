package com.priceguard.workflows.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDto {
    private String productUrl;
    private String productName;
    private double limitPrice;
    private double lastPrice;
    private double minPrice;
}
