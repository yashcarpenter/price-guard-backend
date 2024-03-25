package com.priceguard.workflows.services;

import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.ProductRepository;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    public List<Product> getAllProducts(){
        List<Product> allProducts = new ArrayList<>();
        allProducts = productRepository.findAll();
        return allProducts;
    }

    public List<Product> getProductsByEmail(String email){ return productRepository.findByUserEmail(email); }

    public Product addProduct(ProductDto productDto) {
        User user = userRepository.findByEmail(productDto.getUserEmail()).orElse(null);
        String url = productDto.getUrl();
        if (url != null && !url.isEmpty() && user!=null) {
            Product product = new Product();
            user.setEmail(productDto.getUserEmail());
            userRepository.save(user);
            product.setProductUrl(productDto.getUrl());
            product.setProductName(productDto.getProductName());
            product.setLimitPrice(productDto.getLimitPrice());
            product.setUser(user);
            product.setMinPrice(500);
            return productRepository.save(product);
        }
        return null;
    }

    public void removeProduct(String url) {
        productRepository.deleteById(url);
    }

    public Product updatePrice(double price, Product product) {
        Product existingProduct = productRepository.findById(product.getProductUrl()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setLimitPrice(price);
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(String userEmail, String productUrl){
        Product product = productRepository.findByUserEmailAndProductUrl(userEmail, productUrl);
        productRepository.delete(product);
    }

}
