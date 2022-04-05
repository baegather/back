package com.example.baegether.exceptions;

//요청한 정보를 찾을 수 없을 때 발생하는 Exception입니다.
public class NoSuchDataException extends RuntimeException{
    public NoSuchDataException() {
    }

    public NoSuchDataException(String message) {
        super(message);
    }

    public NoSuchDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchDataException(Throwable cause) {
        super(cause);
    }

    public NoSuchDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
