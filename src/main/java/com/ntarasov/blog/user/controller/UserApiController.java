package com.ntarasov.blog.user.controller;

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
    public List<UserResponse> search(
            @RequestParam (required = false) String query,
            @RequestParam (required = false, defaultValue = "1") Integer size,
            @RequestParam (required = false, defaultValue = "0") Long skip
   ){
        return UserMapping.getInstance().getSearch().convert(
                userApiService.search(query, size, skip)
        );

   }
   @PutMapping(UserApiRoutes.BY_ID)
    public UserFullResponse updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest updateUserRequest
   ) throws UserNotExistException {
        return UserMapping.getInstance().getResponseFull().convert(
                userApiService.update(updateUserRequest)
        );

   }
   @DeleteMapping(UserApiRoutes.BY_ID)
    public String deleteUser(@PathVariable ObjectId id){
        userApiService.delete(id);
        return HttpStatus.OK.toString();

   }
}
