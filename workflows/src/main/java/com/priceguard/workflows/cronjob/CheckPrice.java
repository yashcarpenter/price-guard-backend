package com.priceguard.workflows.cronjob;

import com.priceguard.core.dao.ProductDao;
import com.priceguard.core.dao.ProductPriceDao;
import com.priceguard.core.dao.UserProductDao;
import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.entities.UserProducts;
import com.priceguard.workflows.services.EmailService;
import com.priceguard.workflows.services.amazonpricefetchingservice.AmazonApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class CheckPrice {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductPriceDao productPriceDao;

    @Autowired
    private UserProductDao userProductDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AmazonApiService amazonApiService;

    @Scheduled(fixedRate = 36000000)
    private void fetchPricesFromDatabase() {
        List<Product> products = productDao.findAll();

        for (Product product : products) {
            String asin = product.getAsin();
            Double lastPrice = product.getLastPrice();
            Double minPrice = product.getMinPrice();
            Double currentPrice = amazonApiService.getProductPrice(asin);
            List<UserProducts> userProductsList = userProductDao.findProductByAsin(asin);

            product.setLastPrice(currentPrice);
            productDao.save(product);

            ProductPrice productPrice = new ProductPrice();
            productPrice.setPrice(currentPrice);
            productPrice.setAsin(new Product(asin));
            productPrice.setTimestamp(LocalDateTime.now());
            productPriceDao.save(productPrice);

            for (UserProducts userProducts : userProductsList) {
                Double limitPrice = userProducts.getLimitPrice();
                String email = userProducts.getUser().getEmail();
                if (currentPrice < limitPrice) {
                    sendEmail(email, currentPrice, userProducts.getProductName());
                    log.info("Mail sent to " + email + " because current price is below " + limitPrice);
                }
            }

            if (currentPrice < minPrice) {
                product.setMinPrice(currentPrice);
                productDao.save(product);
                for (UserProducts userProducts : userProductsList) {
                    String email = userProducts.getUser().getEmail();
                    sendEmail(email, currentPrice, userProducts.getProductName());
                    log.info("Mail sent to " + email + " because price decreased to " + currentPrice);
                }
            }
        }
        log.info("Fetching prices from the database...");
    }

    private void sendEmail(String to, Double price, String productName) {
        try{
            emailService.sendEmail(to, price, productName);
        } catch (Exception e){

        }

    }
}
