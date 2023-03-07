package com.springboot3.sample.rest.annotation;

import com.springboot3.sample.rest.model.RoleNames;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    RoleNames[] value();
}
