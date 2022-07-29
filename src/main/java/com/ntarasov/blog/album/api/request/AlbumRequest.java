package com.ntarasov.blog.album.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "AlbumRequest", description = "Model album request")

public class AlbumRequest {
        private ObjectId id;
        private String  title;
        private ObjectId ownerId;
}
