package com.omgservers.router.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideNotFoundException extends ServerSideClientException {

    public ServerSideNotFoundException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideNotFoundException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideNotFoundException(final ExceptionQualifierEnum qualifier,
                                       final String message,
                                       final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideNotFoundException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideNotFoundException(final ExceptionQualifierEnum qualifier,
                                          final String message,
                                          final Throwable cause,
                                          final boolean enableSuppression,
                                          final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}