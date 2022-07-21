package com.ntarasov.blog.article.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "ArticleRequest", description = "Model article request")

public class ArticleRequest {
        private ObjectId id;
        private String  titel;
        private String  body;
        private ObjectId  ownerld;
}
