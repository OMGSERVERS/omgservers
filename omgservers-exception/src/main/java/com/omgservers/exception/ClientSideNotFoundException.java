package com.omgservers.exception;

import java.util.Optional;

public class ClientSideNotFoundException extends ServerSideNotFoundException {

    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideNotFoundException(String errorText, ExceptionErrorResponse exceptionErrorResponse) {
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
