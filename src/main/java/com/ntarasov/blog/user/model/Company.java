package com.ntarasov.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Company", description = "Model company")

public class Company {
    private String name;
    private Address address;
}
