package com.omgservers.service.exception;

public class ServerSideBadRequestException extends ServerSideClientExceptionException {
    public ServerSideBadRequestException() {
        super();
    }

    public ServerSideBadRequestException(String message) {
        super(message);
    }

    public ServerSideBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideBadRequestException(Throwable cause) {
        super(cause);
    }

    protected ServerSideBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
