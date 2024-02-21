package com.priceguard.backend.controller;


import com.priceguard.backend.Services.ProductService;
import com.priceguard.backend.dto.ProductDto;
import com.priceguard.backend.entities.Product;
import com.priceguard.backend.repository.ProductRepository;
import com.priceguard.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Product>> getProductsByUserName(@PathVariable String userEmail) {
        List<Product> products = productRepository.findByUserEmail(userEmail);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product addedProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updatePrice/{productId}")
    public ResponseEntity<Product> updateLimitPrice(@PathVariable double price, @RequestBody Product product) {
        Product updatedProduct = productService.updatePrice(price, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<?> updateEmail(@RequestParam String url, @PathVariable String email) {
        Product product = productRepository.findByUserEmailAndProductUrl(email,url);
        productRepository.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




