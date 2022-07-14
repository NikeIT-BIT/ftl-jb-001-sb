package com.ntarasov.blog.user.api.response;

import com.ntarasov.blog.user.model.Address;
import com.ntarasov.blog.user.model.Company;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder

public class UserFullResponse extends UserResponse{
    private Address address;
    private Company company;


}
