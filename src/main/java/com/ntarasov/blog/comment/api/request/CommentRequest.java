package com.ntarasov.blog.comment.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "CommentRequest", description = "Model comment request")

public class CommentRequest {
        private ObjectId id;
        private ObjectId articleId;
        private ObjectId userId;
        private String messege;
}
