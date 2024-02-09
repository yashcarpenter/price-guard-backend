package com.priceguard.interfaces;

import com.priceguard.dto.UserDto;
import com.priceguard.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userName")
    @Mapping(target = "name", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    @Mapping(target = "mobileNumber")
    @Mapping(target = "email")
    @Mapping(target = "password")

    UserDto mapToProfileDTO(User user);
}

