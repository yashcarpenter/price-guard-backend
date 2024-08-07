package com.priceguard.workflows.controller;

import com.priceguard.core.entities.User;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.workflows.services.EmailService;
import com.priceguard.workflows.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/getdetail/{email}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable String email) {
        ApiResponse<User> response = userService.getUser(email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/getalluser")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/update/email")
    public ResponseEntity<ApiResponse<User>> updateEmail(@RequestParam String newEmail, @RequestParam String email) {
        ApiResponse<User> response = userService.updateEmail(newEmail, email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/update/password")
    public ResponseEntity<ApiResponse<User>> updatePassword(@RequestParam String newPassword, @RequestParam String email) {
        ApiResponse<User> response = userService.updatePassword(newPassword, email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/update/mobilenumber")
    public ResponseEntity<ApiResponse<User>> updateMobileNumber(@RequestParam String newMobileNumber, @RequestParam String email) {
        ApiResponse<User> response = userService.updateMobileNumber(newMobileNumber, email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/update/username")
    public ResponseEntity<ApiResponse<User>> updateUserName(@RequestParam String newUserName, @RequestParam String email) {
        ApiResponse<User> response = userService.updateUserName(newUserName, email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/sendemail")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestParam String to, @RequestParam Double price, @RequestParam String productName) {
        ApiResponse<String> response = userService.sendEmail(to, price, productName);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

