package com.ntarasov.blog.user.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "UserResponse", description = "Model userResponse")

public class UserResponse {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
}
