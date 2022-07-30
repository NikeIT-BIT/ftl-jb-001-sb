package com.ntarasov.blog.auth.controller;

import com.ntarasov.blog.auth.api.request.AuthRequest;
import com.ntarasov.blog.auth.api.response.AuthResponse;
import com.ntarasov.blog.auth.exceptions.AuthException;
import com.ntarasov.blog.auth.routes.AuthRoutes;
import com.ntarasov.blog.auth.service.AuthService;
import com.ntarasov.blog.base.api.response.OkResponse;
import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.api.response.UserFullResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.mapping.UserMapping;
import com.ntarasov.blog.user.service.UserApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController

public class AuthController {

    private final UserApiService userApiService;
    private final AuthService authService;
    @PostMapping(AuthRoutes.REGISTRATION)
    @ApiOperation(value = "registr", notes = "User this when you need register and new create user")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "User alreade exist")
    })
    public OkResponse<UserFullResponse> registration(@RequestBody RegistrationRequest request) throws UserExistException {
        return OkResponse.of(UserMapping.getInstance().getResponseFull().convert(userApiService.registration(request)));

    }

    @PostMapping(AuthRoutes.AUTH)
    @ApiOperation(value = "authorization", notes = "Get token")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "User not exist"),
            @ApiResponse(code = 401, message = "Bad password")
    })
    public OkResponse<AuthResponse> auth(@RequestBody AuthRequest request) throws AuthException, UserNotExistException {
        return OkResponse.of(authService.auth(request));
    }
}
