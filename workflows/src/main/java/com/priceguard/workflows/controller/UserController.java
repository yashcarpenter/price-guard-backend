package com.priceguard.workflows.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.nylas.models.NylasApiError;
import com.nylas.models.NylasSdkTimeoutError;
import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.UserDto;
import com.priceguard.workflows.services.EmailService;
import com.priceguard.workflows.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/getUser/{email}")
    public User getUser(@PathVariable String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestParam String newEmail, @RequestParam String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setEmail(newEmail);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam String newPassword, @RequestParam String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setPassword(newPassword);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updateMobileNumber")
    public ResponseEntity<?> updateMobileNumber(@RequestParam String newMobileNumber, @RequestParam String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setMobileNumber(newMobileNumber);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/updateUserName")
    public ResponseEntity<?> updateUserName(@RequestParam String newUserName, @RequestParam String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        User newUser = foundUser.get();
        newUser.setUserName(newUserName);
        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @GetMapping("/sendEmail")
    public void sendEmail()  {
        emailService.sendEmail();
    }
}

