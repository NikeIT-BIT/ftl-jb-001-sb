package com.ntarasov.blog.todoTask.api.request;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter

public class TodoTaskSearchRequest extends SearchRequest {
    private ObjectId ownerId;
}
