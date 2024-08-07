package com.priceguard.workflows.services.impl;

import com.priceguard.core.dao.UserDao;
import com.priceguard.core.entities.User;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.workflows.services.EmailService;
import com.priceguard.workflows.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailService emailService;

    public ApiResponse<User> getUser(String email) {
        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.OK.value())
                    .message("User found")
                    .data(user.get())
                    .build();
        } else {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
    }

    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userDao.findAll();
        return ApiResponse.<List<User>>builder()
                .status(HttpStatus.OK.value())
                .message("All users fetched successfully")
                .data(users)
                .build();
    }

    public ApiResponse<User> updateEmail(String newEmail, String email) {
        Optional<User> foundUser = userDao.findByEmail(email);
        if (foundUser.isPresent()) {
            User newUser = foundUser.get();
            newUser.setEmail(newEmail);
            User updatedUser = userDao.save(newUser);
            return ApiResponse.<User>builder()
                    .status(HttpStatus.OK.value())
                    .message("Email updated successfully")
                    .data(updatedUser)
                    .build();
        } else {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
    }

    public ApiResponse<User> updatePassword(String newPassword, String email) {
        Optional<User> foundUser = userDao.findByEmail(email);
        if (foundUser.isPresent()) {
            User newUser = foundUser.get();
            newUser.setPassword(newPassword);
            User updatedUser = userDao.save(newUser);
            return ApiResponse.<User>builder()
                    .status(HttpStatus.OK.value())
                    .message("Password updated successfully")
                    .data(updatedUser)
                    .build();
        } else {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
    }

    public ApiResponse<User> updateMobileNumber(String newMobileNumber, String email) {
        Optional<User> foundUser = userDao.findByEmail(email);
        if (foundUser.isPresent()) {
            User newUser = foundUser.get();
            newUser.setMobileNumber(newMobileNumber);
            User updatedUser = userDao.save(newUser);
            return ApiResponse.<User>builder()
                    .status(HttpStatus.OK.value())
                    .message("Mobile number updated successfully")
                    .data(updatedUser)
                    .build();
        } else {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
    }

    public ApiResponse<User> updateUserName(String newUserName, String email) {
        Optional<User> foundUser = userDao.findByEmail(email);
        if (foundUser.isPresent()) {
            User newUser = foundUser.get();
            newUser.setUserName(newUserName);
            User updatedUser = userDao.save(newUser);
            return ApiResponse.<User>builder()
                    .status(HttpStatus.OK.value())
                    .message("Username updated successfully")
                    .data(updatedUser)
                    .build();
        } else {
            return ApiResponse.<User>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("User not found")
                    .data(null)
                    .build();
        }
    }

    public ApiResponse<String> sendEmail(String to, Double price, String productName) {
        try{
            emailService.sendEmail(to, price, productName);
        } catch (Exception exception){
            return ApiResponse.<String>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to send Email")
                    .data(exception.getMessage())
                    .build();
        }

        return ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Email sent successfully")
                .data("Email Sent Successfully")
                .build();
    }
}

