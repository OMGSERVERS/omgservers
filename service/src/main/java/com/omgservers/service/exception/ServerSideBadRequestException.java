package com.omgservers.service.exception;

import com.omgservers.model.exception.ExceptionQualifierEnum;

public class ServerSideBadRequestException extends ServerSideClientException {

    public ServerSideBadRequestException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideBadRequestException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideBadRequestException(final ExceptionQualifierEnum qualifier,
                                         final String message,
                                         final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideBadRequestException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideBadRequestException(final ExceptionQualifierEnum qualifier,
                                            final String message,
                                            final Throwable cause,
                                            final boolean enableSuppression,
                                            final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}
