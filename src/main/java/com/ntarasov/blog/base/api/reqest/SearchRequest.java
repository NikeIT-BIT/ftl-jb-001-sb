package com.ntarasov.blog.base.api.reqest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SearchRequest {
    protected String query;
    protected Integer size;
    protected Long skip;
}
