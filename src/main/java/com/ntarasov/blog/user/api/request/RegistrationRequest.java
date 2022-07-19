package com.ntarasov.blog.user.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "RegistrationRequest", description = "Model user registration")

public class RegistrationRequest {
    private String email;
    private String password;
}
