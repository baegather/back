package com.example.baegether.exceptions;


//인증,인가되지 않은 유저가 어떤 로직을 수행하려 할 때 발생하는 클래스 입니다.
public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException() {
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedUserException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
