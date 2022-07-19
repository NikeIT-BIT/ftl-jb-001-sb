package com.ntarasov.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Address", description = "Model address")

public class Address {
    private String city;
    private String street;
    private String suite;
    private String zipcode;
    private Point point;
}
