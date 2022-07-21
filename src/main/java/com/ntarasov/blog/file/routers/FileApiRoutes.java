package com.ntarasov.blog.file.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class FileApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/file";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/files/{id}";

}
