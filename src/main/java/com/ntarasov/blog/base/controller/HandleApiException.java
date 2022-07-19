package com.ntarasov.blog.base.controller;

import com.ntarasov.blog.base.api.response.ErrorResponse;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.exception.UserNotExistException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class HandleApiException extends ResponseEntityExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest webRequest){
        return buildResponseEntity(ErrorResponse.of("NotFoundExeption", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Object> userNotExistException(UserNotExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserNotExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> userExistException(UserExistException ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("UserExistException", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest webRequest) {
        return buildResponseEntity(ErrorResponse.of("Exception", HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
