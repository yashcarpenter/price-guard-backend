package com.priceguard.workflows.services;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.dto.RequestProductDto;
import com.priceguard.workflows.dto.ResponseProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public List<UserProducts> getAllProducts();
    public UserProducts addProduct(RequestProductDto requestProductDto);
    public List<ResponseProductDto> getProductDataOfUserByEmail(String userEmail);
    public UserProducts updatePrice(double price, String userEmail, String productAsin);
    public void deleteProduct(String userEmail, String productAsin);
}




