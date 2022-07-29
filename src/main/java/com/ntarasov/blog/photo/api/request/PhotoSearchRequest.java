package com.ntarasov.blog.photo.api.request;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class PhotoSearchRequest extends SearchRequest {
    private ObjectId albumId;

}
