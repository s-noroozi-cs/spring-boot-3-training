package com.springboot3.sample.rest.mapper;

import com.springboot3.sample.rest.controller.user.FetchUserResponse;
import com.springboot3.sample.rest.controller.user.UserCreateRequest;
import com.springboot3.sample.rest.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-06T21:07:27+0330",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User userCreateModelToUser(UserCreateRequest request) {
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

    @Override
    public FetchUserResponse userToFetchUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        FetchUserResponse fetchUserResponse = new FetchUserResponse();

        fetchUserResponse.setId( user.getId() );
        fetchUserResponse.setName( user.getName() );
        fetchUserResponse.setAddress( user.getAddress() );
        fetchUserResponse.setUsername( user.getUsername() );
        fetchUserResponse.setCreationTime( user.getCreationTime() );
        fetchUserResponse.setLastModificationTime( user.getLastModificationTime() );

        return fetchUserResponse;
    }
}
