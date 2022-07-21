package com.ntarasov.blog.article.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class ArticleApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/article";
    public static final String BY_ID = ROOT + "/{id}";

}
