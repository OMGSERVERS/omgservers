package com.omgservers.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import java.io.IOException;

public class ClientSideExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    final ObjectMapper objectMapper;

    public ClientSideExceptionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        int statusCode = response.getStatus();
        String errorText = response.readEntity(String.class);
        ExceptionErrorResponse exceptionErrorResponse;
        try {
            exceptionErrorResponse = objectMapper.readValue(errorText, ExceptionErrorResponse.class);
            errorText = exceptionErrorResponse.getMessage();
        } catch (IOException e) {
            exceptionErrorResponse = null;
        }

        if (statusCode >= 400 && statusCode < 500) {
            if (statusCode == Response.Status.BAD_REQUEST.getStatusCode()) {
                return new ClientSideBadRequestException(errorText, exceptionErrorResponse);
            } else if (statusCode == Response.Status.UNAUTHORIZED.getStatusCode()) {
                return new ClientSideUnauthorizedException(errorText, exceptionErrorResponse);
            } else if (statusCode == Response.Status.NOT_FOUND.getStatusCode()) {
                return new ClientSideNotFoundException(errorText, exceptionErrorResponse);
            } else if (statusCode == Response.Status.CONFLICT.getStatusCode()) {
                return new ClientSideConflictException(errorText, exceptionErrorResponse);
            } else if (statusCode == Response.Status.GONE.getStatusCode()) {
                return new ClientSideGoneException(errorText, exceptionErrorResponse);
            }
        } else if (statusCode >= 500) {
            return new ClientSideInternalException(errorText, exceptionErrorResponse);
        }

        return new ClientSideHttpException(statusCode, errorText, exceptionErrorResponse);
    }
}
