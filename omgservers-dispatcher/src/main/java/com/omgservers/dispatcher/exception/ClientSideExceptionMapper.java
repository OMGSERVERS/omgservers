package com.omgservers.dispatcher.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionErrorResponse;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import java.io.IOException;

@Slf4j
public class ClientSideExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    final ObjectMapper objectMapper;

    public ClientSideExceptionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        final var statusCode = response.getStatus();
        final var responseAsString = response.readEntity(String.class);

        try {
            final var exceptionErrorResponse = objectMapper.readValue(responseAsString, ExceptionErrorResponse.class);
            final var exceptionQualifier = exceptionErrorResponse.getQualifier();

            if (statusCode >= 400 && statusCode < 500) {
                if (statusCode == Response.Status.BAD_REQUEST.getStatusCode()) {
                    return new ServerSideBadRequestException(exceptionQualifier);
                } else if (statusCode == Response.Status.UNAUTHORIZED.getStatusCode()) {
                    return new ServerSideUnauthorizedException(exceptionQualifier);
                } else if (statusCode == Response.Status.FORBIDDEN.getStatusCode()) {
                    return new ServerSideForbiddenException(exceptionQualifier);
                } else if (statusCode == Response.Status.NOT_FOUND.getStatusCode()) {
                    return new ServerSideNotFoundException(exceptionQualifier);
                } else if (statusCode == Response.Status.CONFLICT.getStatusCode()) {
                    return new ServerSideConflictException(exceptionQualifier);
                } else if (statusCode == Response.Status.GONE.getStatusCode()) {
                    return new ServerSideGoneException(exceptionQualifier);
                }
            } else if (statusCode >= 500) {
                return new ServerSideInternalException(exceptionQualifier);
            }

        } catch (IOException e) {
            log.warn("Client side exception mapping failed, statusCode={}, responseAsString={}, {}",
                    statusCode, responseAsString, e.getMessage());
        }

        return new ClientSideHttpException(statusCode, responseAsString);
    }
}
