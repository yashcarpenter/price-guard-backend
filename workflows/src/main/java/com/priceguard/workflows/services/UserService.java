package com.priceguard.workflows.services;

import com.priceguard.core.entities.User;
import com.priceguard.core.repository.UserRepository;
import com.priceguard.workflows.dto.RegistrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> createUser(RegistrationRequestDto registrationRequestDto){
        try {
            if (userRepository.findByUserName(registrationRequestDto.getUserName()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            User user = new User();
            user.setUserName(registrationRequestDto.getUserName());
            user.setName(registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName());
            user.setMobileNumber(registrationRequestDto.getMobileNumber());
            user.setEmail(registrationRequestDto.getEmail());
            user.setPassword(registrationRequestDto.getPassword());

            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }

}
