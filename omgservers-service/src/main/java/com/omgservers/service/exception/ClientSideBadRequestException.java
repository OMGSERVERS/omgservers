package com.omgservers.service.exception;

public class ClientSideBadRequestException extends ServerSideBadRequestException {

    final String errorText;
    final ExceptionErrorResponse errorResponse;

    public ClientSideBadRequestException(final ExceptionErrorResponse exceptionErrorResponse) {
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
