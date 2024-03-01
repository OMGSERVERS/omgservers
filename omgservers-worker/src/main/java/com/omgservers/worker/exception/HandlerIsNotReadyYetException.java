package com.omgservers.worker.exception;

public class HandlerIsNotReadyYetException extends RuntimeException {

    public HandlerIsNotReadyYetException(String message) {
        super(message);
    }
}
