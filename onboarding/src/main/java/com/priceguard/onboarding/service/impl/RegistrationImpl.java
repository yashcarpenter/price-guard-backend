package com.priceguard.onboarding.service.impl;

import com.priceguard.core.dao.UserDao;
import com.priceguard.core.entities.User;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.onboarding.dto.UserDetailRegistrationDto;
import com.priceguard.onboarding.service.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class RegistrationImpl implements Registration {

    @Autowired
    private UserDao userDao;

    public ApiResponse<String> addUserDetail(UserDetailRegistrationDto dto) {

        User user = userDao.findUserByEmail(dto.getEmail());

        if (Objects.isNull(user)) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User not found. Please register with email Id.", null);
        }
        if (!user.isEmailVerified()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Please verify your email.", null);
        }
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNumber(dto.getMobileNumber());
        userDao.addUserDetail(user);
        return new ApiResponse<>(HttpStatus.OK.value(), "User details updated successfully!", null);
    }

}

