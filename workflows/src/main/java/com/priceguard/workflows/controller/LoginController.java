package com.priceguard.workflows.controller;

import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.services.PriceScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PriceScraperService priceScraperService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (Objects.equals(user.getEmail(), email)) {
                if (Objects.equals(user.getPassword(), password)) {
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Password");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Email");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        }
    }

    @GetMapping("/get-price")
    public void getPrice() throws IOException {
        priceScraperService.run();
    }

}