package com.omgservers.service.exception;

public class ServerSideNotFoundException extends ServerSideClientExceptionException {
    public ServerSideNotFoundException() {
        super();
    }

    public ServerSideNotFoundException(String message) {
        super(message);
    }

    public ServerSideNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ServerSideNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
