package com.springboot3.sample.rest.mapper;


import com.springboot3.sample.rest.controller.user.UserCreateRequest;
import com.springboot3.sample.rest.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User convertUserCreateModel(UserCreateRequest request);
}