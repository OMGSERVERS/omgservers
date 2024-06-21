package com.omgservers.service.integration.jenkins.impl.operation.getJenkinsClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ClientSideHttpException;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideInternalException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Slf4j
public class JenkinsExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    final ObjectMapper objectMapper;

    public JenkinsExceptionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        final var statusCode = response.getStatus();
        final var responseAsString = response.readEntity(String.class);

        log.warn("Jenkins request failed, statusCode={}", statusCode);

        if (statusCode >= 400 && statusCode < 500) {
            return new ServerSideBadRequestException(ExceptionQualifierEnum.JENKINS_REQUEST_FAILED);
        } else if (statusCode >= 500) {
            return new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED);
        }

        return new ClientSideHttpException(statusCode, responseAsString);
    }
}
