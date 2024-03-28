package com.priceguard.workflows.controller;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserProductRepository;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.RequestProductDto;
import com.priceguard.workflows.dto.ResponseProductDto;
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
    private UserProductRepository userProductRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getall")
    public ResponseEntity<List<UserProducts>> getAllProducts() {
        List<UserProducts> userProducts = productService.getAllProducts();
        return new ResponseEntity<>(userProducts, HttpStatus.OK);
    }

    @PostMapping("/{userEmail}")
    public List<ResponseProductDto> getProductsByUserEmail(@PathVariable String userEmail) {
        return productService.getProductDataOfUserByEmail(userEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<UserProducts> addProduct(@RequestBody RequestProductDto requestProductDto) {
        UserProducts addedUserProducts = productService.addProduct(requestProductDto);
        return new ResponseEntity<>(addedUserProducts, HttpStatus.CREATED);
    }

    @PostMapping("/updatePrice/{productId}")
    public ResponseEntity<UserProducts> updateLimitPrice(@RequestParam double price,
                                                         @RequestParam String userEmail,
                                                         @RequestParam String productUrl) {
        UserProducts updatedUserProducts = productService.updatePrice(price, userEmail, productUrl);
        if (updatedUserProducts != null) {
            return new ResponseEntity<>(updatedUserProducts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<?> updateEmail(@RequestParam String url, @PathVariable String email) {
        UserProducts userProducts = userProductRepository.findByUserEmailAndProductUrlUrl(email,url);
        userProductRepository.delete(userProducts);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}




