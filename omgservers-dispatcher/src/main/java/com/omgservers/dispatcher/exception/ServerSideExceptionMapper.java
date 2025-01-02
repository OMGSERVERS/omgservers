package com.omgservers.dispatcher.exception;

import com.omgservers.schema.model.exception.ExceptionErrorResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Objects;

@Slf4j
@ApplicationScoped
public class ServerSideExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> illegalArgumentException(final IllegalArgumentException e,
                                                                         final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.WRONG_ARGUMENT);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> constraintViolationException(final ConstraintViolationException e,
                                                                             final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum
                .VALIDATION_CONSTRAINT_VIOLATED);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> webApplicationException(final WebApplicationException e,
                                                                        final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        if (Objects.nonNull(e.getResponse()) && e.getResponse().getStatus() == 400) {
            final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.WRONG_REQUEST);
            return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
        } else {
            return throwable(e, uriInfo);
        }
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> notSupportedException(final NotSupportedException e,
                                                                      final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum.WRONG_MEDIA_TYPE);
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> badRequestException(final ServerSideBadRequestException e,
                                                                    final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.BAD_REQUEST, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> unauthorizedServerException(final ServerSideUnauthorizedException e,
                                                                            final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.UNAUTHORIZED, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> forbiddenServerException(final ServerSideForbiddenException e,
                                                                         final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.FORBIDDEN, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> notFoundException(final ServerSideNotFoundException e,
                                                                  final UriInfo uriInfo) {
        log.debug("Client side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.NOT_FOUND, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> conflictException(final ServerSideConflictException e,
                                                                  final UriInfo uriInfo) {
        log.debug("Server side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.CONFLICT, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> goneException(final ServerSideGoneException e,
                                                              final UriInfo uriInfo) {
        log.debug("Server side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.GONE, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> internalException(final ServerSideInternalException e,
                                                                  final UriInfo uriInfo) {
        log.error("Server side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(e.getQualifier());
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ExceptionErrorResponse> throwable(final Throwable e,
                                                          final UriInfo uriInfo) {
        log.error("Server side exception, {}, {}:{}", uriInfo.getAbsolutePath(),
                e.getClass().getSimpleName(),
                e.getMessage());

        final var exceptionErrorResponse = new ExceptionErrorResponse(ExceptionQualifierEnum
                .INTERNAL_EXCEPTION_OCCURRED);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, exceptionErrorResponse);
    }
}
