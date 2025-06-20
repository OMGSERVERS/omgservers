package com.omgservers.connector.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideConflictException extends ServerSideClientException {

    public ServerSideConflictException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideConflictException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideConflictException(final ExceptionQualifierEnum qualifier,
                                       final String message,
                                       final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideConflictException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideConflictException(final ExceptionQualifierEnum qualifier,
                                          final String message,
                                          final Throwable cause,
                                          final boolean enableSuppression,
                                          final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}

