package com.omgservers.ctl.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ctl.exception.CommandException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Slf4j
class ClientSideExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    final ObjectMapper objectMapper;

    public ClientSideExceptionMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RuntimeException toThrowable(final Response response) {
        final var statusCode = response.getStatus();
        final var responseAsString = response.readEntity(String.class);
        return new CommandException("status=" + statusCode + ", response=" + responseAsString);
    }
}
