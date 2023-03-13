package com.springboot3.sample.rest.config;

import com.springboot3.sample.rest.model.RoleNames;

import java.util.List;
import java.util.Map;

public interface ApiKeys {
    String CREATE_USER_API_KEY = "create-user-2023-03-07";
    String FETCH_USER_API_KEY = "fetch-user-2023-03-07";
    String DELETE_USER_API_KEY = "delete-user-2023-03-08";
    String TEST_USER_API_KEY = "test-user-2023-03-08";

    Map<String, List<RoleNames>> roleMapping = Map.of(
            CREATE_USER_API_KEY, List.of(RoleNames.CREATE_USER)
            , FETCH_USER_API_KEY, List.of(RoleNames.FETCH_USER)
            , DELETE_USER_API_KEY, List.of(RoleNames.DELETE_USER)
            ,TEST_USER_API_KEY,List.of(RoleNames.TEST_ROLE)
    );
}
