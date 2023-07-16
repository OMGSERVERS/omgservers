package com.omgservers.application.exception;

import java.util.Optional;

public class ClientSideInternalException extends ServerSideInternalException {

    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideInternalException(String errorText, ExceptionErrorResponse exceptionErrorResponse) {
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
