package com.omgservers.service.exception;

public class ClientSideInternalException extends ServerSideInternalException {

    public ClientSideInternalException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
