package com.example.baegether.exceptions;

<<<<<<< HEAD
//인증,인가되지 않은 유저가 어떤 로직을 수행하려 할 때 발생하는 클래스 입니다.
=======
>>>>>>> ca9b9362c07ce21b32c88662fee4bb60902dbaaf
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
