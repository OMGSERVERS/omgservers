package com.omgservers.application.exception;

import java.util.Optional;

public class ClientSideGoneException extends ServerSideGoneException {

    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideGoneException(String errorText, ExceptionErrorResponse exceptionErrorResponse) {
        super(errorText);
        this.errorResponse = Optional.ofNullable(exceptionErrorResponse);
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    public Optional<ExceptionErrorResponse> getErrorResponse() {
        return errorResponse;
    }
}
