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

import java.time.LocalDateTime;
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

    @Autowired
    ProductPriceScraperService productPriceScraperService;

    public List<UserProducts> getAllProducts(){
        return userProductRepository.findAll();
    }

    @Transactional // Ensure all operations in this method are performed within a single transaction
    public UserProducts addProduct(RequestProductDto requestProductDto) {
        User user = userRepository.findByEmail(requestProductDto.getUserEmail()).orElse(null);
        String asin = requestProductDto.getProductAsin();
        if (asin != null && !asin.isEmpty() && user != null) {
            // Check if the product already exists
            Product product = productRepository.findById(asin).orElse(null);
            if (product == null) {
                double minPrice = productPriceScraperService.getPrice(asin);
                Product newProduct = new Product(asin,minPrice,minPrice, LocalDateTime.now());
                product = productRepository.save(newProduct);
            }
            UserProducts userProducts = new UserProducts();
            userProducts.setUser(user);
            userProducts.setProductAsin(product);
            userProducts.setProductName(requestProductDto.getProductName());
            userProducts.setLimitPrice(requestProductDto.getLimitPrice());
            userProducts.setAddedAt(LocalDateTime.now());
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
                responseProductDto.setLastPrice(userProduct.getProductAsin().getLastPrice());
                responseProductDto.setProductAsin(userProduct.getProductAsin().getAsin());
                responseProductDto.setMinPrice(userProduct.getProductAsin().getMinPrice());
                responseProductDto.setProductAddedAt(userProduct.getAddedAt());
                responseProductDto.setLastMinPriceAt(userProduct.getProductAsin().getLastMinPriceAt());
                responseProductDtos.add(responseProductDto);
            }
        }
        return responseProductDtos;
    }

    public UserProducts updatePrice(double price,String userEmail,String productAsin) {
        UserProducts existingUserProducts = userProductRepository.findByUserEmailAndProductAsinAsin(userEmail, productAsin);
        if (existingUserProducts != null) {
            existingUserProducts.setLimitPrice(price);
            return userProductRepository.save(existingUserProducts);
        }
        return null;
    }

    public void deleteProduct(String userEmail, String productAsin){
        UserProducts userProducts = userProductRepository.findByUserEmailAndProductAsinAsin(userEmail, productAsin);
        List<UserProducts> userProductsList= userProductRepository.findByProductAsinAsin(productAsin);
        if (userProducts != null) {
            userProductRepository.delete(userProducts);
        }
        if(userProductsList.size() == 1){
            productRepository.deleteById(productAsin);
        }
    }
}



