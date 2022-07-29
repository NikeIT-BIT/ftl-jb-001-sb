package com.ntarasov.blog.comment.api.request;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class CommentSearchRequest extends SearchRequest {
    private ObjectId articleId;
    private ObjectId userId;

}
