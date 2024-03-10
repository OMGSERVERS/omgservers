package com.omgservers.service.exception;

public class ClientSideConflictException extends ServerSideConflictException {

    public ClientSideConflictException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
