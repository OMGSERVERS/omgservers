package com.omgservers.service.exception;

import com.omgservers.schema.model.exception.ExceptionErrorResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException e) {
        log.warn("Unauthorized exception, {}:{}", e.getClass().getSimpleName(), e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.WRONG_CREDENTIALS);
        return Response.status(Response.Status.UNAUTHORIZED.getStatusCode())
                .entity(exceptionErrorResponse).build();
    }
}
