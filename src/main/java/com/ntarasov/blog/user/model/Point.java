package com.ntarasov.blog.user.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@ApiModel(value = "Point", description = "Model point")

public class Point {
    private Double lat;
    private Double lng;
}
