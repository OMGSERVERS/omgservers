package com.omgservers.service.exception;

public class ClientSideUnauthorizedException extends ServerSideUnauthorizedException {

    final String errorText;
    final ExceptionErrorResponse errorResponse;

    public ClientSideUnauthorizedException(final ExceptionErrorResponse exceptionErrorResponse) {
        super(exceptionErrorResponse.getQualifier());
        this.errorResponse = exceptionErrorResponse;
        this.errorText = exceptionErrorResponse.getMessage();
    }

    public String getErrorText() {
        return errorText;
    }

    public ExceptionErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
