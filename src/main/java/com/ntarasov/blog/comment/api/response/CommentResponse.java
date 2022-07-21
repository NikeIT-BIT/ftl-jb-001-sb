package com.ntarasov.blog.comment.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "CommentResponse", description = "Model commentResponse")

public class CommentResponse {
        protected String id;
        protected String articleId;
        protected String userId;
        protected String messege;
}
