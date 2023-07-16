package com.omgservers.application.exception;

import java.util.Optional;

public class ClientSideHttpException extends RuntimeException {

    final int statusCode;
    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideHttpException(int statusCode, String errorText, ExceptionErrorResponse exceptionErrorResponse) {
        super(String.format("rest client error, statusCode=%d, text=%s", statusCode, errorText));
        this.statusCode = statusCode;
        this.errorResponse = Optional.ofNullable(exceptionErrorResponse);
        this.errorText = errorText;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public Optional<ExceptionErrorResponse> getErrorResponse() {
        return errorResponse;
    }
}
