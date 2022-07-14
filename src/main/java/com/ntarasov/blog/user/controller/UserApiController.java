package com.ntarasov.blog.user.controller;

import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.api.response.UserFullResponse;
import com.ntarasov.blog.user.api.response.UserResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.mapping.UserMapping;
import com.ntarasov.blog.user.model.UserDoc;
import com.ntarasov.blog.user.routers.UserApiRoutes;
import com.ntarasov.blog.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserApiService userApiService;

    @PostMapping(UserApiRoutes.ROOT)
    public UserFullResponse registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return UserMapping.getInstance().getResponseFull().convert(userApiService.registration(request));
    }
    @GetMapping(UserApiRoutes.BY_ID)
   public UserFullResponse byId(@PathVariable ObjectId id) throws ChangeSetPersister.NotFoundException {
        return UserMapping.getInstance().getResponseFull().convert(
                userApiService.findById(id).orElseThrow(
                        ChangeSetPersister.NotFoundException::new)
        );
   }

   @GetMapping(UserApiRoutes.ROOT)
    public List<UserResponse> search(){
        return UserMapping.getInstance().getSearch().convert(
                userApiService.search()
        );

   }
}
