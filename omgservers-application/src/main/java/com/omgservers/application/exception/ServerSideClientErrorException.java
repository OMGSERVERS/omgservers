
package com.omgservers.application.exception;

public class ServerSideClientErrorException extends RuntimeException {

    public ServerSideClientErrorException() {
        super();
    }

    public ServerSideClientErrorException(String message) {
        super(message);
    }

    public ServerSideClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideClientErrorException(Throwable cause) {
        super(cause);
    }

    protected ServerSideClientErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
