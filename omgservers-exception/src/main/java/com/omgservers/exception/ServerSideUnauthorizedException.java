package com.omgservers.exception;

public class ServerSideUnauthorizedException extends ServerSideClientErrorException {
    public ServerSideUnauthorizedException() {
        super();
    }

    public ServerSideUnauthorizedException(String message) {
        super(message);
    }

    public ServerSideUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideUnauthorizedException(Throwable cause) {
        super(cause);
    }

    protected ServerSideUnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
