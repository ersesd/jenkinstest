package com.sparta.collabobo.global.exception;

public class NotExistsUserException extends RuntimeException {
    public NotExistsUserException() {
        super("유저가 존재하지 않습니다.");
    }

}
