package com.ntarasov.blog.user.api.response;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder

public class UserResponse {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
}
