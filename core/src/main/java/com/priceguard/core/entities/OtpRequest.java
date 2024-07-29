package com.priceguard.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OtpRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String ipAddress;
    private String userAgent;
    private String platform;
    private String language;
    private String deviceType; // e.g., mobile, tablet, desktop
    private String osName; // Operating System name
    private String osVersion; // Operating System version
    private String browserName; // Browser name
    private String browserVersion; // Browser version
    private String deviceId; // Device identifier if available
    private String screenResolution; // e.g., 1920x1080
    private String hardwareInfo; // Additional hardware information if available
    private String networkType; // Network type (e.g., WiFi, Cellular)
    private LocalDateTime time;
}
