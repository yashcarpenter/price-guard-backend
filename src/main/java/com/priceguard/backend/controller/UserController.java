package com.priceguard.backend.controller;

import com.priceguard.backend.dto.UserDto;
import com.priceguard.backend.entities.User;
import com.priceguard.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
//    @Autowired
//    private PasswordEncoder passwordEncoder; // Assuming you have a PasswordEncoder bean configured
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
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

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}

