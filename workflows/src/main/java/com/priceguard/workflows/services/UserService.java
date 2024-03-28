package com.priceguard.workflows.services;

import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createUser(UserDto userDto){
        try {
            if (userRepository.findByUserName(userDto.getUserName()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            User user = new User();
            user.setUserName(userDto.getUserName());
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setMobileNumber(userDto.getMobileNumber());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());

            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

}
