package com.omgservers.router.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideInternalException extends ServerSideBaseException {

    public ServerSideInternalException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideInternalException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideInternalException(final ExceptionQualifierEnum qualifier,
                                       final String message,
                                       final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideInternalException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideInternalException(final ExceptionQualifierEnum qualifier,
                                          final String message,
                                          final Throwable cause,
                                          final boolean enableSuppression,
                                          final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}
