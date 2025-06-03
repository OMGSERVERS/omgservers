package com.omgservers.connector.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideGoneException extends ServerSideClientException {

    public ServerSideGoneException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideGoneException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideGoneException(final ExceptionQualifierEnum qualifier,
                                   final String message,
                                   final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideGoneException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideGoneException(final ExceptionQualifierEnum qualifier,
                                      final String message,
                                      final Throwable cause,
                                      final boolean enableSuppression,
                                      final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}
