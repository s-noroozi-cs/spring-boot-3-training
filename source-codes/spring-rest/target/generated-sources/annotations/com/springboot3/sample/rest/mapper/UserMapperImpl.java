package com.springboot3.sample.rest.mapper;

import com.springboot3.sample.rest.controller.user.UserCreateRequest;
import com.springboot3.sample.rest.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-04T20:21:38+0330",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User convertUserCreateModel(UserCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setName( request.getName() );
        user.setAddress( request.getAddress() );
        user.setUsername( request.getUsername() );
        user.setPassword( request.getPassword() );

        return user;
    }
}
