package com.omgservers.service.exception;

public class ServerSideUnauthorizedException extends ServerSideClientException {

    public ServerSideUnauthorizedException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideUnauthorizedException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideUnauthorizedException(final ExceptionQualifierEnum qualifier,
                                           final String message,
                                           final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideUnauthorizedException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideUnauthorizedException(final ExceptionQualifierEnum qualifier,
                                              final String message,
                                              final Throwable cause,
                                              final boolean enableSuppression,
                                              final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}