package com.sparta.collabobo.global.exception;

public class NotMatchedUserException extends RuntimeException {
    public NotMatchedUserException() {
        super("유저가 존재하지 않습니다.");
    }
}
