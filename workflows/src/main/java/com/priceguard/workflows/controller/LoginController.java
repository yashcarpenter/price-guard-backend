package com.priceguard.workflows.controller;

import com.priceguard.core.dao.UserDao;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        User user =  userDao.findUserByEmail(email);
        if (Objects.nonNull(user)) {
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
}