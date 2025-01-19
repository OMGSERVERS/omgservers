package com.omgservers.service.entrypoint.registry.impl.service.webService.impl.registryApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.registry.HandleEventsRegistryRequest;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.entrypoint.registry.impl.service.webService.WebService;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RegistryApiImpl implements RegistryApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;
    final ObjectMapper objectMapper;

    @Override
    @PermitAll
    public Uni<Void> handleEvents(@NotNull JsonNode jsonNode) {
        log.debug("Registry events were received, {}", jsonNode.toString());

        try {
            final var request = objectMapper.treeToValue(jsonNode, HandleEventsRegistryRequest.class);
            return handleApiRequestOperation.handleApiRequest(log, request, webService::handleEvents);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_REQUEST, e.getMessage(), e);
        }
    }
}

