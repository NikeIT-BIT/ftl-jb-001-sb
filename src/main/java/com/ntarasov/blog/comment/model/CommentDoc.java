package com.ntarasov.blog.comment.model;

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

public class CommentDoc {
    @Id
        private ObjectId id;
        private ObjectId articleId;
        private ObjectId userId;
        private String messege;
}
