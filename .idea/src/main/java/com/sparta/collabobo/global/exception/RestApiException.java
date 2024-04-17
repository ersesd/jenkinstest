package com.sparta.collabobo.global.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class RestApiException {

    private String errorMessage;
    private int statusCode;

    public static ResponseEntity<RestApiException> of(String errorMessage, int statusCode) {
        return ResponseEntity.status(statusCode)
            .body(new RestApiException(errorMessage, statusCode));
    }

    public RestApiException(String message, int value) {
        this.errorMessage = message;
        this.statusCode = value;
    }
}
