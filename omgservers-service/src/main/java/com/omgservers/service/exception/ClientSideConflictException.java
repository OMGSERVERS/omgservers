package com.omgservers.service.exception;

public class ClientSideConflictException extends ServerSideConflictException {

    final String errorText;
    final ExceptionErrorResponse errorResponse;

    public ClientSideConflictException(final ExceptionErrorResponse exceptionErrorResponse) {
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
