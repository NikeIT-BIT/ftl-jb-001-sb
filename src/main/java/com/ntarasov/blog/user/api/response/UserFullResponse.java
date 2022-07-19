package com.ntarasov.blog.user.api.response;

import com.ntarasov.blog.user.model.Address;
import com.ntarasov.blog.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ApiModel(value = "UserFullResponse", description = "Model userFullResponse")

public class UserFullResponse extends UserResponse{
    private Address address;
    private Company company;
}
