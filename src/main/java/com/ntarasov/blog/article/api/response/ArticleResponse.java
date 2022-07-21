package com.ntarasov.blog.article.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "ArticleResponse", description = "Model articleResponse")

public class ArticleResponse {
        protected String id;
        protected String  titel;
        protected String  body;
        protected String  ownerld;
}
