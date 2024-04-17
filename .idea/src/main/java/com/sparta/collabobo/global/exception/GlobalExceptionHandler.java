package com.sparta.collabobo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotExistsUserException.class, NotExistsEnumException.class, NotFoundUserException.class})
    public ResponseEntity<RestApiException> notExistsException(Exception e) {
        return RestApiException.of(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler({NotMatchedUserException.class})
    public ResponseEntity<RestApiException> notMatchedException(Exception e) {
        return RestApiException.of(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
