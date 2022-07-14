package com.ntarasov.blog.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class UserResponse {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
}
