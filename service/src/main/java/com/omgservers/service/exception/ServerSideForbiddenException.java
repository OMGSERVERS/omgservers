package com.omgservers.service.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideForbiddenException extends ServerSideClientException {

    public ServerSideForbiddenException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideForbiddenException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideForbiddenException(final ExceptionQualifierEnum qualifier,
                                        final String message,
                                        final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideForbiddenException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideForbiddenException(final ExceptionQualifierEnum qualifier,
                                           final String message,
                                           final Throwable cause,
                                           final boolean enableSuppression,
                                           final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}
