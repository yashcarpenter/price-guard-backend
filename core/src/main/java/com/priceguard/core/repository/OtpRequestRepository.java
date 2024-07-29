package com.priceguard.core.repository;

import com.priceguard.core.entities.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRequestRepository extends JpaRepository<OtpRequest, Long> {
//    public List<OtpRequest> findByEmail(String email);
}
