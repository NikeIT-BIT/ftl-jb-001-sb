package com.ntarasov.blog.comment.api.request;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter

public class CommentSearchRequest extends SearchRequest {
    private ObjectId articleId;
    private ObjectId userId;

}
