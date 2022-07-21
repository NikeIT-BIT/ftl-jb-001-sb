package com.ntarasov.blog.article.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ArticleDoc {
    @Id
        private ObjectId id;
        private String  titel;
        private String  body;
        private ObjectId  ownerld;
}
