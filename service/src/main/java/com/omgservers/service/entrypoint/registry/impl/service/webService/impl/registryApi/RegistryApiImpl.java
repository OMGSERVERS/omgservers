package com.omgservers.service.entrypoint.registry.impl.service.webService.impl.registryApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.registry.HandleEventsRegistryRequest;
import com.omgservers.service.entrypoint.registry.impl.service.webService.WebService;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SUPPORT})
public class RegistryApiImpl implements RegistryApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;
    final ObjectMapper objectMapper;

    @Override
    @PermitAll
    public Uni<Void> handleEvent(@NotNull JsonNode jsonNode) {
        log.info("Registry event was received, {}", jsonNode.toString());

        try {
            final var request = objectMapper.treeToValue(jsonNode, HandleEventsRegistryRequest.class);
            return handleApiRequestOperation.handleApiRequest(log, request, webService::handleEvent);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.REQUEST_WRONG, e.getMessage(), e);
        }
    }
}

