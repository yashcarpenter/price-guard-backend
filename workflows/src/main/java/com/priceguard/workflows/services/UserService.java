package com.priceguard.workflows.services;

import com.priceguard.core.entities.User;
import com.priceguard.core.util.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse<User> getUser(String email);
    ApiResponse<List<User>> getAllUsers();
    ApiResponse<User> updateEmail(String newEmail, String email);
    ApiResponse<User> updatePassword(String newPassword, String email);
    ApiResponse<User> updateMobileNumber(String newMobileNumber, String email);
    ApiResponse<User> updateUserName(String newUserName, String email);
    ApiResponse<String> sendEmail(String to, Double price, String productName);
}
