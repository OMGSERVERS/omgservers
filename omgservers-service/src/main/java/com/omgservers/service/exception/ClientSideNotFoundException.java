package com.omgservers.service.exception;

public class ClientSideNotFoundException extends ServerSideNotFoundException {

    public ClientSideNotFoundException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
