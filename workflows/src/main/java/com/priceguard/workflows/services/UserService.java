package com.priceguard.workflows.services;

import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createUser(LoginRequestDto loginRequestDto){
        try {
            if (userRepository.findByUserName(loginRequestDto.getUserName()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            if (userRepository.findByEmail(loginRequestDto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            User user = new User();
            user.setUserName(loginRequestDto.getUserName());
            user.setName(loginRequestDto.getFirstName() + " " + loginRequestDto.getLastName());
            user.setMobileNumber(loginRequestDto.getMobileNumber());
            user.setEmail(loginRequestDto.getEmail());
            user.setPassword(loginRequestDto.getPassword());

            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

}
