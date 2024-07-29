package com.priceguard.onboarding.service.impl;

import com.priceguard.core.dao.OtpRequestDao;
import com.priceguard.core.dao.UserDao;
import com.priceguard.core.entities.OtpRequest;
import com.priceguard.core.entities.User;
import com.priceguard.core.enums.UserDataVerificationStatus;
import com.priceguard.core.service.GmailService;
import com.priceguard.core.util.ApiResponse;
import com.priceguard.onboarding.dto.otp.EmailOtpRequestDto;
import com.priceguard.onboarding.dto.otp.VerifyEmailOtpDto;
import com.priceguard.onboarding.service.EmailOtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmailOtpServiceImpl implements EmailOtpService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OtpRequestDao otpRequestDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 8;
    private final Random random = new SecureRandom();

    private static final String KEY_PREFIX = "emailotp:";

    @Value("${otp.expiration.time.minutes:10}")
    private int otpExpirationTimeMinutes;

    @Autowired
    private GmailService gmailService;

    public ApiResponse<String> sendOtpToEmail(String email) {
        if (!isValidEmail(email)) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid email format", null);
        }
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "User Already Exists!", null);
        }
        String otp = generateOtp();
        String subject = "Your OTP for Verification";
        String bodyText = String.format(
                "Dear User,\n\n" +
                        "Thank you for registering with our service.\n\n" +
                        "To complete your registration and verify your email address, please use the following One-Time Password (OTP):\n\n" +
                        "Your OTP: %s\n\n" +
                        "This OTP is valid for the next %d minutes. If you did not request this OTP, please ignore this email.\n\n" +
                        "Important:\n" +
                        "- Do not share this OTP with anyone.\n" +
                        "- If you did not initiate this request, please contact our support team immediately.\n\n" +
                        "Thank you,\n" +
                        "The Support Team", otp, otpExpirationTimeMinutes);
        try {
            gmailService.sendMessage(email, subject, bodyText);
            redisTemplate.opsForValue().set(KEY_PREFIX + email, otp, otpExpirationTimeMinutes, TimeUnit.MINUTES);
            return new ApiResponse<>(HttpStatus.OK.value(), "OTP sent to " + email, null);
        } catch (MessagingException | IOException | GeneralSecurityException e) {
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to send OTP, please try again", null);
        }
    }

    private String generateOtp() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    public void saveOtpRequestDeviceInformation(EmailOtpRequestDto dto) {
        OtpRequest otpRequest = OtpRequest.builder()
                .email(dto.getEmail())
                .ipAddress(dto.getIpAddress())
                .userAgent(dto.getUserAgent())
                .platform(dto.getPlatform())
                .language(dto.getLanguage())
                .deviceType(dto.getDeviceType())
                .osName(dto.getOsName())
                .osVersion(dto.getOsVersion())
                .browserName(dto.getBrowserName())
                .browserVersion(dto.getBrowserVersion())
                .deviceId(dto.getDeviceId())
                .screenResolution(dto.getScreenResolution())
                .hardwareInfo(dto.getHardwareInfo())
                .networkType(dto.getNetworkType())
                .time(LocalDateTime.now()) // Sets the current timestamp
                .build();
        otpRequestDao.saveOtpRequest(otpRequest);
    }

    public ApiResponse<UserDataVerificationStatus.OtpStatus> verifyEmailOtp(VerifyEmailOtpDto verifyEmailOtpDto) {
        String otp = redisTemplate.opsForValue().get(KEY_PREFIX + verifyEmailOtpDto.getEmail());

        if (otp == null) {
            return new ApiResponse<>(HttpStatus.GONE.value(), "OTP has expired or is invalid.", UserDataVerificationStatus.OtpStatus.TIMEOUT);
        }

        if (otp.equals(verifyEmailOtpDto.getOtp())) {
            User user = User.builder()
                    .email(verifyEmailOtpDto.getEmail())
                    .password(verifyEmailOtpDto.getPassword())
                    .emailVerified(true)
                    .build();
            userDao.saveUser(user);
            return new ApiResponse<>(HttpStatus.OK.value(), "Email verified successfully.", UserDataVerificationStatus.OtpStatus.VERIFIED);
        } else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid OTP.", UserDataVerificationStatus.OtpStatus.REJECTED);
        }
    }
}
