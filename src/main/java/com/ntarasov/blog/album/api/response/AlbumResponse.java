package com.ntarasov.blog.album.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "AlbumResponse", description = "Model albumResponse")

public class AlbumResponse {
        protected String id;
        protected String  title;
        protected String ownerId;
}
