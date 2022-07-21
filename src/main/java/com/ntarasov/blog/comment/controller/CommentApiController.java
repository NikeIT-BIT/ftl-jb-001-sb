package com.ntarasov.blog.comment.controller;

import com.ntarasov.blog.article.exception.ArticleNotExistException;
import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.comment.api.request.CommentRequest;
import com.ntarasov.blog.comment.api.request.CommentSearchRequest;
import com.ntarasov.blog.comment.api.response.CommentResponse;
import com.ntarasov.blog.comment.exception.CommentExistException;
import com.ntarasov.blog.comment.exception.CommentNotExistException;
import com.ntarasov.blog.comment.mapping.CommentMapping;
import com.ntarasov.blog.comment.routers.CommentApiRoutes;
import com.ntarasov.blog.comment.service.CommentApiService;
import com.ntarasov.blog.user.exception.UserNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Comment API")
public class CommentApiController {
    private final CommentApiService commentApiService;



    @GetMapping(CommentApiRoutes.BY_ID)
    @ApiOperation(value = "Find comment by id", notes = "Comment this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    public OkResponse<CommentResponse> byId(
    @ApiParam(value = "Comment id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(CommentMapping.getInstance().getResponse().convert(
            commentApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }


    @PostMapping(CommentApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Comment this when you need create and new create comment")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Comment alreade exist")
    })
    public OkResponse<CommentResponse> create(@RequestBody CommentRequest request) throws CommentExistException, UserNotExistException, ArticleNotExistException {
        return OkResponse.of(CommentMapping.getInstance().getResponse().convert(commentApiService.create(request)));
    }


   @GetMapping(CommentApiRoutes.ROOT)
   @ApiOperation(value = "Search comment", notes = "Comment this when you need find comment by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<SearchResponse<CommentResponse>> search(
           @ModelAttribute CommentSearchRequest request
           ){
        return OkResponse.of(CommentMapping.getInstance().getSearch().convert(
                commentApiService.search(request)
        ));

   }


   @PutMapping(CommentApiRoutes.BY_ID)
   @ApiOperation(value = "Update comment", notes = "Comment this when you need update comment info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Comment ID invalid")
   })
    public OkResponse<CommentResponse> comment(
            @ApiParam(value = "Comment id") @PathVariable String id,
            @RequestBody CommentRequest commentRequest
   ) throws CommentNotExistException {
        return OkResponse.of(CommentMapping.getInstance().getResponse().convert(
                commentApiService.update(commentRequest)
        ));

   }


   @DeleteMapping(CommentApiRoutes.BY_ID)
   @ApiOperation(value = "Delete comment", notes = "User this when you need delete comment")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        commentApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());

   }
}
