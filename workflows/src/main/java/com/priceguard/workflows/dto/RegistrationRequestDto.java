package com.priceguard.workflows.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String userName;
}