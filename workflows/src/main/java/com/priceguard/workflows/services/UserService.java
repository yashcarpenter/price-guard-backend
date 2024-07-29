package com.priceguard.workflows.services;

import com.priceguard.core.entities.User;
import com.priceguard.core.util.ApiResponse;

import java.util.List;

public interface UserService {
    public ApiResponse<User> getUser(String email);
    public ApiResponse<List<User>> getAllUsers();
    public ApiResponse<User> updateEmail(String newEmail, String email);
    public ApiResponse<User> updatePassword(String newPassword, String email);
    public ApiResponse<User> updateMobileNumber(String newMobileNumber, String email);
    public ApiResponse<User> updateUserName(String newUserName, String email);
    public ApiResponse<String> sendEmail(String to, Double price, String productName);
}
