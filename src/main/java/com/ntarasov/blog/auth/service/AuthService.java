package com.ntarasov.blog.auth.service;

import com.ntarasov.blog.auth.api.request.AuthRequest;
import com.ntarasov.blog.auth.entity.CustomUserDetails;
import com.ntarasov.blog.auth.exceptions.AuthException;
import com.ntarasov.blog.security.JwtProvider;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.model.UserDoc;
import com.ntarasov.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public CustomUserDetails loadUserByEmail(String email) throws UserNotExistException {
        UserDoc userDoc = userRepository.findByEmail(email).orElseThrow(UserNotExistException::new);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userDoc);
    }
    public String auth(AuthRequest authRequest) throws UserNotExistException, AuthException {
        UserDoc userDoc = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(UserNotExistException::new);
        if(userDoc.getPassword().equals(UserDoc.hexPassword(authRequest.getPassword())) == false){
            userDoc.setFailLogin(userDoc.getFailLogin() + 1);
            userRepository.save(userDoc);
            throw new AuthException();
        }
        if(userDoc.getFailLogin() > 0){
            userDoc.setFailLogin(0);
            userRepository.save(userDoc);
        }

        String token = jwtProvider.generateToken(authRequest.getEmail());
        return token;
    }
}
