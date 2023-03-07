package com.springboot3.sample.rest.config;

import com.springboot3.sample.rest.model.RoleNames;

import java.util.List;
import java.util.Map;

public interface ApiKeys {
    Map<String, List<RoleNames>> roleMapping = Map.of(
            "create-user-2023-03-07",List.of(RoleNames.CREATE_USER)
            ,"fetch-user-2023-03-07",List.of(RoleNames.FETCH_USER)
            ,"admin-2023-03-07",List.of(RoleNames.values())
    );
}
