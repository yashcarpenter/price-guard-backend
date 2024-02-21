package com.priceguard.backend.controller;

import com.priceguard.backend.dto.UserDto;
import com.priceguard.backend.entities.User;
import com.priceguard.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

//    @Autowired
//    private PasswordEncoder passwordEncoder; // Assuming you have a PasswordEncoder bean configured
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

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

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody Map<String, String> requestBody) {
        String newEmail = requestBody.get("newEmail");
        String email = requestBody.get("email");
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setEmail(newEmail);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("newPassword");
        String email = requestBody.get("email");
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setPassword(newPassword);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updateMobileNumber")
    public ResponseEntity<?> updateMobileNumber(@RequestBody Map<String, String> requestBody) {
        String newMobileNumber = requestBody.get("newMobileNumber");
        String email = requestBody.get("email");
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setMobileNumber(newMobileNumber);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updateUserName")
    public ResponseEntity<?> updateUserName(@RequestBody Map<String, String> requestBody) {
        String newUserName = requestBody.get("newUserName");
        String email = requestBody.get("email");
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setUserName(newUserName);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

}

