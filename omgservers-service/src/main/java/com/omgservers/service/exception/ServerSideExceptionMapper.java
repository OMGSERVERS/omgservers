package com.omgservers.service.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.io.IOException;

@Slf4j
@ApplicationScoped
public class ServerSideExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> illegalArgumentException(final IllegalArgumentException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.ILLEGAL_ARGUMENT,
                e.getMessage());
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> constraintViolationException(final ConstraintViolationException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.CONSTRAINT_VIOLATION,
                e.getMessage());
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> badRequestException(final ServerSideBadRequestException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> unauthorizedServerException(final ServerSideUnauthorizedException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.UNAUTHORIZED, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> forbiddenServerException(final ServerSideForbiddenException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.FORBIDDEN, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> notFoundException(final ServerSideNotFoundException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.NOT_FOUND, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> conflictException(final ServerSideConflictException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.CONFLICT, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> goneException(final ServerSideGoneException e) {
        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.GONE, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> internalException(final ServerSideInternalException e) {
        log.error("Internal exception, {}:{}", e.getClass().getSimpleName(), e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> throwable(final Throwable e) throws IOException {
        log.error("Uncaught exception, {}:{}", e.getClass().getSimpleName(), e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.INTERNAL_EXCEPTION,
                e.getMessage());
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }
}
