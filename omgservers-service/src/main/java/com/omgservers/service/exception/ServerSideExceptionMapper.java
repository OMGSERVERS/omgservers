package com.omgservers.service.exception;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.io.IOException;

@Slf4j
@ApplicationScoped
public class ServerSideExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> badRequestException(ServerSideBadRequestException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> unauthorizedServerException(ServerSideUnauthorizedException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.UNAUTHORIZED, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> forbiddenServerException(ServerSideForbiddenException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.FORBIDDEN, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> notFoundException(ServerSideNotFoundException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.NOT_FOUND, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> conflictException(ServerSideConflictException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.CONFLICT, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> goneException(ServerSideGoneException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.GONE, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> internalException(ServerSideInternalException e) {
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> throwable(Throwable e) throws IOException {
        log.error("{}", e.getMessage(), e);
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(e);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }
}
