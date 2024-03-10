package com.omgservers.service.exception;

public class ClientSideGoneException extends ServerSideGoneException {

    final String errorText;
    final ExceptionErrorResponse errorResponse;

    public ClientSideGoneException(final ExceptionErrorResponse exceptionErrorResponse) {
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
