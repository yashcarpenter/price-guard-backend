package com.priceguard.workflows.controller;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.dto.RequestProductDto;
import com.priceguard.workflows.dto.ResponseProductDto;
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

    @PostMapping("/{userEmail}")
    public List<ResponseProductDto> getProductsByUserEmail(@PathVariable String userEmail) {
        return productService.getProductDataOfUserByEmail(userEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<UserProducts> addProduct(@RequestBody RequestProductDto requestProductDto) {
        UserProducts addedUserProducts = productService.addProduct(requestProductDto);
        return new ResponseEntity<>(addedUserProducts, HttpStatus.CREATED);
    }

    @PostMapping("/product/update/limitprice")
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

    @PostMapping("/product/delete")
    public ResponseEntity<?> deleteProductOfaUser(@RequestParam String productAsin,
                                                  @RequestParam String email) {
        productService.deleteProduct(email, productAsin);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}




