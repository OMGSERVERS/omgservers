package com.omgservers.application.exception;

import java.util.Optional;

public class ClientSideBadRequestException extends ServerSideBadRequestException {

    final String errorText;
    final Optional<ExceptionErrorResponse> errorResponse;

    public ClientSideBadRequestException(String errorText, ExceptionErrorResponse exceptionErrorResponse) {
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
