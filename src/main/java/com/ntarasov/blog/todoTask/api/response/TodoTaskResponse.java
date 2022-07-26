package com.ntarasov.blog.todoTask.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "TodoTaskResponse", description = "Model todoTaskResponse")

public class TodoTaskResponse {
        protected String id;
        protected String  title;
        protected String ownerId;
        protected String completed;
        protected List<String> files;
}
