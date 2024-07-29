package com.priceguard.onboarding.service;

import com.priceguard.core.util.ApiResponse;
import com.priceguard.onboarding.dto.UserDetailRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public interface Registration {
    public ApiResponse<String> addUserDetail(UserDetailRegistrationDto userDetailRegistrationDto);
}
