package com.ntarasov.blog.photo.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class PhotoApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/photo";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/photo/{id}";
}
