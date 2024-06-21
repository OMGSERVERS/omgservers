package com.omgservers.service.exception;

public class ServerSideBaseException extends RuntimeException {

    final ExceptionQualifierEnum qualifier;

    public ServerSideBaseException(final ExceptionQualifierEnum qualifier) {
        super("server throws exception, reason=" + qualifier);
        this.qualifier = qualifier;
    }

    public ServerSideBaseException(final ExceptionQualifierEnum qualifier, final String message) {
        super(message);
        this.qualifier = qualifier;
    }

    public ServerSideBaseException(final ExceptionQualifierEnum qualifier,
                                   final String message,
                                   final Throwable cause) {
        super(message, cause);
        this.qualifier = qualifier;
    }

    public ServerSideBaseException(final ExceptionQualifierEnum qualifier,
                                   final Throwable cause) {
        super(cause);
        this.qualifier = qualifier;
    }

    protected ServerSideBaseException(final ExceptionQualifierEnum qualifier,
                                      final String message,
                                      final Throwable cause,
                                      final boolean enableSuppression,
                                      final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.qualifier = qualifier;
    }

    public ExceptionQualifierEnum getQualifier() {
        return qualifier;
    }
}
