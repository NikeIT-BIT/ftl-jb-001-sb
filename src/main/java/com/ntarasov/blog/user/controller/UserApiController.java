package com.ntarasov.blog.user.controller;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.api.request.UpdateUserRequest;
import com.ntarasov.blog.user.api.response.UserFullResponse;
import com.ntarasov.blog.user.api.response.UserResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.mapping.UserMapping;
import com.ntarasov.blog.user.routers.UserApiRoutes;
import com.ntarasov.blog.user.service.UserApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "User API")
public class UserApiController {
    private final UserApiService userApiService;

    @GetMapping(UserApiRoutes.BY_ID)
    @ApiOperation(value = "Find user by id", notes = "User this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "User not found")
    })
   public OkResponse<UserFullResponse> byId(
            @ApiParam(value = "User id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.findById(id).orElseThrow(
                        ChangeSetPersister.NotFoundException::new)
        ));
   }


   @GetMapping(UserApiRoutes.ROOT)
   @ApiOperation(value = "Search user", notes = "User this when you need find user by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<SearchResponse<UserResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(UserMapping.getInstance().getSearch().convert(
                userApiService.search(request)
        ));

   }


   @PutMapping(UserApiRoutes.BY_ID)
   @ApiOperation(value = "Update user", notes = "User this when you need update user info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "User ID invalid")
   })
    public OkResponse<UserFullResponse> updateUser(
            @ApiParam(value = "User id") @PathVariable String id,
            @RequestBody UpdateUserRequest updateUserRequest
   ) throws UserNotExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.update(updateUserRequest)
        ));

   }


   @DeleteMapping(UserApiRoutes.BY_ID)
   @ApiOperation(value = "Delete user", notes = "User this when you need delete user")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })
    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        userApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());

   }
}
