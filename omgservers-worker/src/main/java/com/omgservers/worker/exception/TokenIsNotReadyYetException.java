package com.omgservers.worker.exception;

public class TokenIsNotReadyYetException extends RuntimeException {

    public TokenIsNotReadyYetException(String message) {
        super(message);
    }
}
