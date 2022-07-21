package com.ntarasov.blog.article.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.article.api.request.ArticleRequest;
import com.ntarasov.blog.article.api.response.ArticleResponse;
import com.ntarasov.blog.article.exception.ArticleExistException;
import com.ntarasov.blog.article.exception.ArticleNotExistException;
import com.ntarasov.blog.article.mapping.ArticleMapping;
import com.ntarasov.blog.article.routers.ArticleApiRoutes;
import com.ntarasov.blog.article.service.ArticleApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Article API")
public class ArticleApiController {
    private final ArticleApiService articleApiService;



    @GetMapping(ArticleApiRoutes.BY_ID)
    @ApiOperation(value = "Find article by id", notes = "Article this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Article not found")
    })
    public OkResponse<ArticleResponse> byId(
            @ApiParam(value = "Article id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(ArticleMapping.getInstance().getResponse().convert(
                articleApiService.findById(id).orElseThrow(
                        ChangeSetPersister.NotFoundException::new)
        ));
    }




    @PostMapping(ArticleApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Article this when you need create and new create article")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Article alreade exist")
    })
    public OkResponse<ArticleResponse> create(@RequestBody ArticleRequest request) throws ArticleExistException, UserNotExistException {
        return OkResponse.of(ArticleMapping.getInstance().getResponse().convert(articleApiService.create(request)));
    }



   @GetMapping(ArticleApiRoutes.ROOT)
   @ApiOperation(value = "Search article", notes = "Article this when you need find article by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<SearchResponse<ArticleResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(ArticleMapping.getInstance().getSearch().convert(
                articleApiService.search(request)
        ));

   }


   @PutMapping(ArticleApiRoutes.BY_ID)
   @ApiOperation(value = "Update article", notes = "Article this when you need update article info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Article ID invalid")
   })
    public OkResponse<ArticleResponse> article(
            @ApiParam(value = "Article id") @PathVariable String id,
            @RequestBody ArticleRequest articleRequest
   ) throws ArticleNotExistException {
        return OkResponse.of(ArticleMapping.getInstance().getResponse().convert(
                articleApiService.update(articleRequest)
        ));

   }


   @DeleteMapping(ArticleApiRoutes.BY_ID)
   @ApiOperation(value = "Delete article", notes = "User this when you need delete article")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        articleApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());

   }
}
