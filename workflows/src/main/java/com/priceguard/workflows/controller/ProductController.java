package com.priceguard.workflows.controller;

import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.ProductRepository;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.ProductDto;
import com.priceguard.workflows.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/{userEmail}")
    public List<Product> getProductsByUserName(@PathVariable String userEmail) {
        List<Product> products = new ArrayList<>();
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            products = user.get().getProducts();
            return products;
        } else return products;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product addedProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/updatePrice/{productId}")
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




