package com.priceguard.workflows.services;

import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.UserProducts;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.ProductRepository;
import com.priceguard.core.repository.UserProductRepository;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.RequestProductDto;
import com.priceguard.workflows.dto.ResponseProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    UserProductRepository userProductRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public List<UserProducts> getAllProducts(){
        return userProductRepository.findAll();
    }

    @Transactional // Ensure all operations in this method are performed within a single transaction
    public UserProducts addProduct(RequestProductDto requestProductDto) {
        User user = userRepository.findByEmail(requestProductDto.getUserEmail()).orElse(null);
        String url = requestProductDto.getUrl();
        if (url != null && !url.isEmpty() && user != null) {
            // Check if the product already exists
            Product product = productRepository.findById(url).orElse(null);
            if (product == null) {
                Product newProduct = new Product(url,500.00,500.00);
                product = productRepository.save(newProduct);
            }
            UserProducts userProducts = new UserProducts();
            userProducts.setUser(user);
            userProducts.setProductUrl(product);
            userProducts.setProductName(requestProductDto.getProductName());
            userProducts.setLimitPrice(requestProductDto.getLimitPrice());
            return userProductRepository.save(userProducts);
        }
        return null;
    }

    public List<ResponseProductDto> getProductDataOfUserByEmail(String userEmail){
        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if(userOptional.isPresent()){
            List<UserProducts> userProducts = userOptional.get().getUserProducts();
            for(UserProducts userProduct : userProducts){
                ResponseProductDto responseProductDto = new ResponseProductDto();
                responseProductDto.setProductName(userProduct.getProductName());
                responseProductDto.setLimitPrice(userProduct.getLimitPrice());
                responseProductDto.setLastPrice(userProduct.getProductUrl().getLastPrice()); // Assuming you have a getLastPrice() method in your Product entity
                responseProductDto.setProductUrl(userProduct.getProductUrl().getUrl());
                responseProductDto.setMinPrice(userProduct.getProductUrl().getMinPrice());
                responseProductDtos.add(responseProductDto);
            }
        }
        return responseProductDtos;
    }

    public UserProducts updatePrice(double price,String userEmail,String productUrl) {
        UserProducts existingUserProducts = userProductRepository.findByUserEmailAndProductUrlUrl(userEmail, productUrl);
        if (existingUserProducts != null) {
            existingUserProducts.setLimitPrice(price);
            return userProductRepository.save(existingUserProducts);
        }
        return null;
    }

    public void deleteProduct(String userEmail, String productUrl){
        UserProducts userProducts = userProductRepository.findByUserEmailAndProductUrlUrl(userEmail, productUrl);
        if (userProducts != null) {
            userProductRepository.delete(userProducts);
        }
    }
}



