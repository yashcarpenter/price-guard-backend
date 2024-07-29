package com.priceguard.core.dao;

import com.priceguard.core.entities.UserProducts;
import com.priceguard.core.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProductDao {
    @Autowired
    private UserProductRepository userProductRepository;

    public List<UserProducts> findAll() {
        return userProductRepository.findAll();
    }

    public UserProducts save(UserProducts userProducts) {
        return userProductRepository.save(userProducts);
    }

    public UserProducts findUserProductByEmailAndAsin(String userEmail, String productAsin) {
        return userProductRepository.findByUserEmailAndProductAsinAsin(userEmail, productAsin);
    }

    public List<UserProducts> findProductByAsin(String productAsin) {
        return userProductRepository.findByProductAsinAsin(productAsin);
    }

    public void delete(UserProducts userProducts) {
        userProductRepository.delete(userProducts);
    }
}
