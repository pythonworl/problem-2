package com.example.contactapp.service.impl;

import com.example.contactapp.io.UserRepository;
import com.example.contactapp.io.entity.UserEntity;
import com.example.contactapp.service.UserService;
import com.example.contactapp.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.contactapp.shared.Utils;

import java.nio.file.attribute.UserPrincipalNotFoundException;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;


    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity storedUserDetails = userRepository.findByEmail(userDto.getEmail());
        if (storedUserDetails != null) {
            throw new RuntimeException("Record already exist");
        }


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        userEntity.setUserId(utils.generateUserId(30));

        UserEntity storedUser = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserDto returnValueDto = new UserDto();
        UserEntity userEntity= userRepository.findByUserId(id);

        BeanUtils.copyProperties(userEntity,returnValueDto);
        return returnValueDto;

    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        UserDto returnValue = new UserDto();
        // UserEntity updatedUser= new UserEntity();

        UserEntity existingUser= userRepository.findByUserId(id);

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        UserEntity updatedUser = userRepository.save(existingUser);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;

    }

    @Override
    public void deletUser(String id) {
        UserEntity userEntity= userRepository.findByUserId(id);
        userRepository.delete(userEntity);


    }

}

