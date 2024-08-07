package com.priceguard.onboarding.service;


import com.priceguard.core.enums.UserDataVerificationStatus;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.onboarding.dto.otp.EmailOtpRequestDto;
import com.priceguard.onboarding.dto.otp.VerifyEmailOtpDto;

public interface EmailOtpService {
    ApiResponse<String> sendOtpToEmail(String email);
    void saveOtpRequestDeviceInformation(EmailOtpRequestDto emailOtpRequestDto);
    ApiResponse<UserDataVerificationStatus.OtpStatus> verifyEmailOtp(VerifyEmailOtpDto verifyEmailOtpDto);
}
