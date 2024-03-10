package com.omgservers.service.exception;

public class ClientSideBadRequestException extends ServerSideBadRequestException {

    public ClientSideBadRequestException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
    }
}
