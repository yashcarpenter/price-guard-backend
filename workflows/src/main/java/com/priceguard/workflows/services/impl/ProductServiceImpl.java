package com.priceguard.workflows.services.impl;

import com.priceguard.core.dao.ProductDao;
import com.priceguard.core.dao.UserDao;
import com.priceguard.core.dao.UserProductDao;
import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.User;
import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.dto.AddProductRequestDto;
import com.priceguard.workflows.dto.AddProductResponseDto;
import com.priceguard.workflows.services.ProductService;
import com.priceguard.workflows.services.amazonpricefetchingservice.AmazonApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AmazonApiService amazonApiService;

    public List<UserProducts> getAllProducts(){
        return userProductDao.findAll();
    }

    @Transactional
    public UserProducts addProduct(AddProductRequestDto addProductRequestDto) {
        User user = userDao.findUserByEmail(addProductRequestDto.getUserEmail());
        String asin = addProductRequestDto.getProductAsin();
        if (asin!=null && !asin.isEmpty() && Objects.nonNull(user)) {
            Product product = productDao.findById(asin).orElse(null);
            if (product == null) {
                double minPrice = amazonApiService.getProductPrice(asin);
                Product newProduct = new Product(asin, minPrice, minPrice, LocalDateTime.now());
                product = productDao.save(newProduct);
            }
            UserProducts userProducts = new UserProducts();
            userProducts.setUser(user);
            userProducts.setProductAsin(product);
            userProducts.setProductName(addProductRequestDto.getProductName());
            userProducts.setLimitPrice(addProductRequestDto.getLimitPrice());
            userProducts.setAddedAt(LocalDateTime.now());
            return userProductDao.save(userProducts);
        }
        return null;
    }

    public List<AddProductResponseDto> getProductDataOfUserByEmail(String userEmail){
        List<AddProductResponseDto> addProductResponseDtos = new ArrayList<>();
        User user = userDao.findUserByEmail(userEmail);
        if (Objects.nonNull(user)) {
            List<UserProducts> userProducts = user.getUserProducts();
            for (UserProducts userProduct : userProducts) {
                AddProductResponseDto addProductResponseDto = new AddProductResponseDto();
                addProductResponseDto.setProductName(userProduct.getProductName());
                addProductResponseDto.setLimitPrice(userProduct.getLimitPrice());
                addProductResponseDto.setLastPrice(userProduct.getProductAsin().getLastPrice());
                addProductResponseDto.setProductAsin(userProduct.getProductAsin().getAsin());
                addProductResponseDto.setMinPrice(userProduct.getProductAsin().getMinPrice());
                addProductResponseDto.setProductAddedAt(userProduct.getAddedAt());
                addProductResponseDto.setLastMinPriceAt(userProduct.getProductAsin().getLastMinPriceAt());
                addProductResponseDtos.add(addProductResponseDto);
            }
        }
        return addProductResponseDtos;
    }

    public UserProducts updatePrice(double price, String userEmail, String productAsin) {
        UserProducts existingUserProducts = userProductDao.findUserProductByEmailAndAsin(userEmail, productAsin);
        if (existingUserProducts != null) {
            existingUserProducts.setLimitPrice(price);
            return userProductDao.save(existingUserProducts);
        }
        return null;
    }

    public void deleteProduct(String userEmail, String productAsin){
        UserProducts userProducts = userProductDao.findUserProductByEmailAndAsin(userEmail, productAsin);
        List<UserProducts> userProductsList = userProductDao.findProductByAsin(productAsin);
        if (userProducts != null) {
            userProductDao.delete(userProducts);
        }
        if (userProductsList.size() == 1) {
            productDao.deleteById(productAsin);
        }
    }
}
