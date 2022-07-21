package com.ntarasov.blog.file.api.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@ApiModel(value = "FileResponse", description = "Model fileResponse")

public class FileResponse {
        protected String id;
        protected String  title;
        protected String ownerId;
}
