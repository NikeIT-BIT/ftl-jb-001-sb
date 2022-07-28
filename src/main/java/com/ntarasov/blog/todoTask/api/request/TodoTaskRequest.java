package com.ntarasov.blog.todoTask.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@ApiModel(value = "TodoTaskRequest", description = "Model todoTask request")

public class TodoTaskRequest {
        private ObjectId id;
        private String  title;
        private ObjectId ownerId;
        private ObjectId completed;
        private List<ObjectId> files;
}
