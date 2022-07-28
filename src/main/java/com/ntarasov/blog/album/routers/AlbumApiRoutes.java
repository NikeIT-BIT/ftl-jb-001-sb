package com.ntarasov.blog.album.routers;

import com.ntarasov.blog.base.routers.BaseApiRouters;

public class AlbumApiRoutes {
    public static final String ROOT = BaseApiRouters.V1 + "/album";
    public static final String BY_ID = ROOT + "/{id}";

}
