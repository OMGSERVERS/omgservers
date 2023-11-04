
package com.omgservers.service.exception;

public class ServerSideClientExceptionException extends RuntimeException {

    public ServerSideClientExceptionException() {
        super();
    }

    public ServerSideClientExceptionException(String message) {
        super(message);
    }

    public ServerSideClientExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerSideClientExceptionException(Throwable cause) {
        super(cause);
    }

    protected ServerSideClientExceptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
