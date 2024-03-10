package com.omgservers.service.exception;

public class ClientSideForbiddenException extends ServerSideUnauthorizedException {

    public ClientSideForbiddenException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
