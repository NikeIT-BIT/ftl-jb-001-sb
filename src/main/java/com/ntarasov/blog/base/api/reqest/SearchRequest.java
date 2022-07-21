package com.ntarasov.blog.base.api.reqest;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SearchRequest {
    @ApiParam(name = "query", value = "Search by fields", required = false)
    protected String query;
    @ApiParam(name = "size", value = "List size(def = 100)", required = false)
    protected Integer size = 100;
    @ApiParam(name = "skip", value = "Search first in search(def = 0)", required = false)
    protected Long skip = 0l;
}
