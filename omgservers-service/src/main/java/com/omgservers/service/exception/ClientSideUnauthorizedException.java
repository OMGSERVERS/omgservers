package com.omgservers.service.exception;

public class ClientSideUnauthorizedException extends ServerSideUnauthorizedException {

    public ClientSideUnauthorizedException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
