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
import com.ntarasov.blog.user.model.UserDoc;
import com.ntarasov.blog.user.routers.UserApiRoutes;
import com.ntarasov.blog.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserApiService userApiService;

    @PostMapping(UserApiRoutes.ROOT)
    public OkResponse<UserFullResponse> registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(userApiService.registration(request)));
    }
    @GetMapping(UserApiRoutes.BY_ID)
   public OkResponse<UserFullResponse> byId(@PathVariable ObjectId id) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.findById(id).orElseThrow(
                        ChangeSetPersister.NotFoundException::new)
        ));
   }

   @GetMapping(UserApiRoutes.ROOT)
    public OkResponse<SearchResponse<UserResponse>> search(
           @ModelAttribute SearchRequest request

           ){
        return OkResponse.of(UserMapping.getInstance().getSearch().convert(
                userApiService.search(request)
        ));

   }
   @PutMapping(UserApiRoutes.BY_ID)
    public OkResponse<UserFullResponse> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest updateUserRequest
   ) throws UserNotExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(
                userApiService.update(updateUserRequest)
        ));

   }
   @DeleteMapping(UserApiRoutes.BY_ID)
    public OkResponse<String> deleteUser(@PathVariable ObjectId id){
        userApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());

   }
}
