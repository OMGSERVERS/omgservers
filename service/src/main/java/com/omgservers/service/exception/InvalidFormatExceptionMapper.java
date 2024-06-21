package com.omgservers.service.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException exception) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.REQUEST_WRONG);
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                .entity(exceptionErrorResponse).build();
    }
}
