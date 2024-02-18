package com.priceguard.backend.Services;

import com.priceguard.backend.dto.UserDto;
import com.priceguard.backend.entities.User;
import com.priceguard.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getFirstName()+" "+userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setMobileNumber(userDto.getMobileNumber());
        userRepository.save(user);
        return user;
    }

}
