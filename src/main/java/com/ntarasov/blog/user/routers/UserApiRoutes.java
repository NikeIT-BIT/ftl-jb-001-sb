package com.ntarasov.blog.user.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class UserApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/user";
    public static final String BY_ID = ROOT + "/{id}";

}
