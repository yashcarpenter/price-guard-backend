package com.priceguard.service;

import com.priceguard.entities.Product;
import com.priceguard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByUserName(String productId) {
        return productRepository.findByUserName(productId);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
//    public Product updateProduct(Product product) {
//        // Check if the product with the given ID exists before updating
//        if (product.getId() != null && productRepository.existsById(product.getId())) {
//            return productRepository.save(product);
//        } else {
//            return null; // Product not found
//        }
//    }
    public void removeProduct(String productId) {
        productRepository.deleteById(productId);
    }
}

