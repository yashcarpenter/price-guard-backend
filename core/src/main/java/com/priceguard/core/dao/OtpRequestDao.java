package com.priceguard.core.dao;

import com.priceguard.core.entities.OtpRequest;
import com.priceguard.core.repository.OtpRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OtpRequestDao {

    @Autowired
    private OtpRequestRepository otpRequestRepository;

    public void saveOtpRequest(OtpRequest otpRequest){
        otpRequestRepository.save(otpRequest);
    }
}
