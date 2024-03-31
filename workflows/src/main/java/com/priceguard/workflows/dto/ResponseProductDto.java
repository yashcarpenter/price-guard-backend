package com.priceguard.workflows.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDto {
    private String productAsin;
    private String productName;
    private double limitPrice;
    private double lastPrice;
    private LocalDateTime lastMinPriceAt;
    private double minPrice;
    private LocalDateTime productAddedAt;
}
