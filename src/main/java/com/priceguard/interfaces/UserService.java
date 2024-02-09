package com.priceguard.interfaces;


import com.priceguard.dto.UserDto;
import com.priceguard.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByUserName(String userName);

    List<UserDto> findAllUsers();
}
