package com.ntarasov.blog.todoTask.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class TodoTaskApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/todoTask";
    public static final String BY_ID = ROOT + "/{id}";

}
