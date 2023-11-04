package com.omgservers.service.exception;

public class ServerSideConflictException extends ServerSideClientExceptionException {
    public ServerSideConflictException() {
        super();
    }

    public ServerSideConflictException(String message) {
        super(message);
    }

    public ServerSideConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideConflictException(Throwable cause) {
        super(cause);
    }

    protected ServerSideConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
