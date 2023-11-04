package com.omgservers.service.exception;

public class ServerSideInternalException extends RuntimeException {
    public ServerSideInternalException() {
        super();
    }

    public ServerSideInternalException(String message) {
        super(message);
    }

    public ServerSideInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideInternalException(Throwable cause) {
        super(cause);
    }

    protected ServerSideInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
