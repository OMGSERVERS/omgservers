package com.omgservers.service.exception;

public class ClientSideGoneException extends ServerSideGoneException {

    public ClientSideGoneException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
