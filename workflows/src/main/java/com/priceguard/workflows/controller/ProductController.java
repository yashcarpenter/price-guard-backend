package com.priceguard.workflows.controller;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.dto.AddProductRequestDto;
import com.priceguard.workflows.dto.AddProductResponseDto;
import com.priceguard.workflows.services.ProductService;
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

    @GetMapping("/getall")
    public ResponseEntity<List<UserProducts>> getAllProducts() {
        List<UserProducts> userProducts = productService.getAllProducts();
        return new ResponseEntity<>(userProducts, HttpStatus.OK);
    }

    @PostMapping("/get/{userEmail}")
    public List<AddProductResponseDto> getProductsByUserEmail(@PathVariable String userEmail) {
        return productService.getProductDataOfUserByEmail(userEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<UserProducts> addProduct(@RequestBody AddProductRequestDto addProductRequestDto) {
        UserProducts addedUserProducts = productService.addProduct(addProductRequestDto);
        return new ResponseEntity<>(addedUserProducts, HttpStatus.CREATED);
    }

    @PostMapping("/update/limitprice")
    public ResponseEntity<UserProducts> updateLimitPrice(@RequestParam double price,
                                                         @RequestParam String userEmail,
                                                         @RequestParam String asin) {
        UserProducts updatedUserProducts = productService.updatePrice(price, userEmail, asin);
        if (updatedUserProducts != null) {
            return new ResponseEntity<>(updatedUserProducts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProductOfaUser(@RequestParam String productAsin,
                                                  @RequestParam String email) {
        productService.deleteProduct(email, productAsin);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}




