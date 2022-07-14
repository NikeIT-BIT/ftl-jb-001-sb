package com.ntarasov.blog.user.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Company {
    private String name;
    private Address address;
}
