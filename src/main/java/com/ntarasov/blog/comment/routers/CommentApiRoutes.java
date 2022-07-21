package com.ntarasov.blog.comment.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class CommentApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/comment";
    public static final String BY_ID = ROOT + "/{id}";

}
