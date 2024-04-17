package com.sparta.collabobo.global.exception;

public class NotExistsEnumException extends RuntimeException{
    public NotExistsEnumException() {
        super("해당 값이 존재하지 않습니다.");
    }
}
