package com.example.baegether.exceptions;

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
