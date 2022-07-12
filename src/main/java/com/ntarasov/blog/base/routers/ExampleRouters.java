package com.ntarasov.blog.base.routers;

public class ExampleRouters {
    public static final String GET = BaseApiRouters.V1 + "/get";
    public static final String GET_WITH_PARAM = BaseApiRouters.V1 + "/get-with-param";
    public static final String GET_WITH_PATH = BaseApiRouters.V1 + "/get-with-path-{pathVariable}";
    public static final String PUT = BaseApiRouters.V1 + "/put/{id}";
    public static final String POST = BaseApiRouters.V1 + "/post";
    public static final String DELETE = BaseApiRouters.V1 + "/delete/{id}";
}
