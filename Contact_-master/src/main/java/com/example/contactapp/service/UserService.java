package com.example.contactapp.service;

import com.example.contactapp.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUser(String email);

    UserDto getUserByUserId(String id);

    UserDto updateUser(String id, UserDto userDto);

    void deletUser(String id);
}
