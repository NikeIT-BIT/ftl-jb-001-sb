package com.ntarasov.blog.user.api.request;

import com.ntarasov.blog.user.model.Address;
import com.ntarasov.blog.user.model.Company;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "UpdateUserRequest", description = "Model user request")

public class UpdateUserRequest {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address = new Address();
    private Company company = new Company();


}
