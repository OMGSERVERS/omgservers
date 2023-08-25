package com.omgservers.exception;

public class ServerSideGoneException extends ServerSideClientErrorException {

    public ServerSideGoneException() {
        super();
    }

    public ServerSideGoneException(String message) {
        super(message);
    }

    public ServerSideGoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideGoneException(Throwable cause) {
        super(cause);
    }

    protected ServerSideGoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
