package com.omgservers.dispatcher.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.omgservers.schema.model.exception.ExceptionErrorResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException e) {
        log.warn("Uncaught exception, {}:{}", e.getClass().getSimpleName(), e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.WRONG_REQUEST);
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                .entity(exceptionErrorResponse).build();
    }
}
