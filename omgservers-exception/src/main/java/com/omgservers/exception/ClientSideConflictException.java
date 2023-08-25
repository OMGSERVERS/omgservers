package com.omgservers.exception;

import java.util.Optional;

public class ClientSideConflictException extends ServerSideConflictException {

    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideConflictException(String errorText, ExceptionErrorResponse exceptionErrorResponse) {
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
