package com.priceguard.workflows.services;

import com.priceguard.core.entities.Product;
import com.priceguard.core.entities.ProductPrice;
import com.priceguard.core.entities.UserProducts;
import com.priceguard.core.repository.ProductPriceRepository;
import com.priceguard.core.repository.ProductRepository;
import com.priceguard.core.repository.UserProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceCheckerService {
    private static final Logger logger = LoggerFactory.getLogger(PriceCheckerService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductPriceScraperService productPriceScraperService;

//    @Scheduled(fixedRate = 36000000)
    private void fetchPricesFromDatabase() {
        List<Product> products = productRepository.findAll();

        for(Product product: products){
            String asin = product.getAsin();
            Double lastPrice = product.getLastPrice();
            Double minPrice = product.getMinPrice();
            Double currentPrice = productPriceScraperService.getPrice(asin);
            List<UserProducts> userProductsList = userProductRepository.findByProductAsinAsin(asin);

            product.setLastPrice(currentPrice);
            productRepository.save(product);

            ProductPrice productPrice = new ProductPrice();
            productPrice.setPrice(currentPrice);
            productPrice.setAsin(new Product(asin));
            productPrice.setTimestamp(LocalDateTime.now());
            productPriceRepository.save(productPrice);

            for(UserProducts userProducts: userProductsList){
                Double limitPrice = userProducts.getLimitPrice();
                String email = userProducts.getUser().getEmail();
                if(currentPrice<limitPrice){
                    sendEmail(email, currentPrice, userProducts.getProductName());
                    logger.info("Mail sent to " + email + " because current price is below" + limitPrice);
                }
            }

            if(currentPrice<minPrice){
                product.setMinPrice(currentPrice);
                productRepository.save(product);
                for(UserProducts userProducts: userProductsList){
                    Double limitPrice = userProducts.getLimitPrice();
                    String email = userProducts.getUser().getEmail();
                    sendEmail(userProducts.getUser().getEmail(), currentPrice, userProducts.getProductName());
                    logger.info("Mail sent to " + email + " because price decreased to " + limitPrice);
                }
            }

        }
        System.out.println("Fetching prices from the database...");
    }

    private void sendEmail(String to, Double price, String productName){
        emailService.sendEmail(to, price, productName);
    }
}
