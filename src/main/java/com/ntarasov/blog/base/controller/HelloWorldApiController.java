package com.ntarasov.blog.base.controller;

import com.ntarasov.blog.base.api.reqest.PostRequest;
import com.ntarasov.blog.base.routers.ExampleRouters;
import com.ntarasov.blog.base.api.response.IndexResponse;
import org.springframework.web.bind.annotation.*;

@RestController

public class HelloWorldApiController {

    @GetMapping(value = "/")
    public IndexResponse index() {
        return IndexResponse.builder()
                .messenger("Hello world")
                .build();

    }

    @GetMapping(ExampleRouters.GET)
    public IndexResponse getExample() {
        return IndexResponse.builder()
                .messenger("Example.GET")
                .build();
    }

    @GetMapping(ExampleRouters.GET_WITH_PARAM)
    public IndexResponse getExampleWithParam(
            @RequestParam String param,
            @RequestParam (value = "p_a_r_a_m") String param2,
            @RequestParam (required = false) String param3,
            @RequestParam (required = false, defaultValue = "def") String param4
    ) {
        return IndexResponse.builder()
                .messenger("ExempleRoots.GET_WITH_PARAM" +
                        "param : " + param + "; " +
                        "param2 : " + param2 + "; " +
                        "param3 : " + param3 + "; " +
                        "param4 : " + param4 + "; "

                )
                .build();
    }
    @GetMapping(ExampleRouters.GET_WITH_PATH)
    public IndexResponse getExampleWithPath(
            @PathVariable String pathVariable
    ){
        return IndexResponse.builder()
                .messenger(pathVariable)
                .build();

    }
    @PostMapping(ExampleRouters.POST)
    public IndexResponse post(
            @RequestBody PostRequest postRequest
    ){
        return IndexResponse.builder()
                .messenger(postRequest.toString())
                .build();
    }
    @PutMapping(ExampleRouters.PUT)
    public IndexResponse put(
            @PathVariable String id,
            @RequestBody PostRequest postRequest
    ){
        return IndexResponse.builder()
                .messenger(id + " " + postRequest.toString())
                .build();
    }
    @DeleteMapping(ExampleRouters.DELETE)
    public IndexResponse delete(
            @PathVariable String id
    ){
        return IndexResponse.builder()
                .messenger(id)
                .build();
    }
}
