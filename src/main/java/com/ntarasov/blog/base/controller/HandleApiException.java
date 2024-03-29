package com.ntarasov.blog.base.controller;

import com.ntarasov.blog.auth.exceptions.AuthException;
import com.ntarasov.blog.auth.exceptions.NotAccessException;
import com.ntarasov.blog.base.api.response.ErrorResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.exception.UserNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationNotSupportedException;

@ControllerAdvice

public class HandleApiException extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest webRequest){
        return buildResponseEntity(ErrorResponse.of("NotFoundExeption", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> badRequest(UserNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("ResponseStatusException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Object> userNotExistException(UserNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistException(UserExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> authException(AuthException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("AuthException", HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(NotAccessException.class)
    public ResponseEntity<Object> notAccessException(NotAccessException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("NotAccessException", HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("Exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
