package com.priceguard.workflows.services;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.dto.AddProductRequestDto;
import com.priceguard.workflows.dto.AddProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<UserProducts> getAllProducts();
    UserProducts addProduct(AddProductRequestDto addProductRequestDto);
    List<AddProductResponseDto> getProductDataOfUserByEmail(String userEmail);
    UserProducts updatePrice(double price, String userEmail, String productAsin);
    void deleteProduct(String userEmail, String productAsin);
}




