
package com.omgservers.router.exception;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;

public class ServerSideClientException extends ServerSideBaseException {

    public ServerSideClientException(final ExceptionQualifierEnum qualifier) {
        super(qualifier);
    }

    public ServerSideClientException(final ExceptionQualifierEnum qualifier, final String message) {
        super(qualifier, message);
    }

    public ServerSideClientException(final ExceptionQualifierEnum qualifier,
                                     final String message,
                                     final Throwable cause) {
        super(qualifier, message, cause);
    }

    public ServerSideClientException(final ExceptionQualifierEnum qualifier, final Throwable cause) {
        super(qualifier, cause);
    }

    protected ServerSideClientException(final ExceptionQualifierEnum qualifier,
                                        final String message,
                                        final Throwable cause,
                                        final boolean enableSuppression,
                                        final boolean writableStackTrace) {
        super(qualifier, message, cause, enableSuppression, writableStackTrace);
    }
}
