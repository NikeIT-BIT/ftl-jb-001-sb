package com.ntarasov.blog.user.controller;

import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.api.response.UserResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.mapping.UserMapping;
import com.ntarasov.blog.user.routers.UserApiRoutes;
import com.ntarasov.blog.user.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserApiService userApiService;

    @PostMapping(UserApiRoutes.ROOT)
    public UserResponse registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return UserMapping.getInstance().getResponseMapping().convert(userApiService.registration(request));
    }
}
