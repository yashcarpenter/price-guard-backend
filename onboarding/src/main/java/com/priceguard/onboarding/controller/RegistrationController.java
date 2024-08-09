package com.priceguard.onboarding.controller;

import com.priceguard.core.dao.UserDao;
import com.priceguard.core.enums.UserDataVerificationStatus;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.onboarding.dto.UserDetailRegistrationDto;
import com.priceguard.onboarding.dto.otp.EmailOtpRequestDto;
import com.priceguard.onboarding.dto.otp.VerifyEmailOtpDto;
import com.priceguard.onboarding.service.EmailOtpService;
import com.priceguard.onboarding.service.Registration;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private Registration registration;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmailOtpService emailOtpService;

    @RequestMapping(value = "/verify/email", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> sendOtpToEmail(@RequestBody EmailOtpRequestDto emailOtpRequestDto){
        ApiResponse<String> response = emailOtpService.sendOtpToEmail(emailOtpRequestDto.getEmail());
        emailOtpService.saveOtpRequestDeviceInformation(emailOtpRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/verify/email/otp", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<UserDataVerificationStatus.OtpStatus>> verifyEmailOtp(@RequestBody VerifyEmailOtpDto verifyEmailOtpDto){
        ApiResponse<UserDataVerificationStatus.OtpStatus> status = emailOtpService.verifyEmailOtp(verifyEmailOtpDto);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @RequestMapping(value = "/add_user_detail", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody UserDetailRegistrationDto userDetailRegistrationDto) {
        ApiResponse<String> message = registration.addUserDetail(userDetailRegistrationDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
