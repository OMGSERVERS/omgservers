package com.omgservers.service.entrypoint.registry.impl.service.webService.impl.registryApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.BasicAuthRegistryResponse;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryRequest;
import com.omgservers.schema.entrypoint.registry.getToken.OAuth2RegistryResponse;
import com.omgservers.schema.entrypoint.registry.handleEvents.HandleEventsRegistryRequest;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.registry.impl.service.webService.WebService;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
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
    public Uni<Void> handleEvents(@NotNull JsonNode jsonNode) {
        log.info("Registry events were received, {}", jsonNode.toString());

        try {
            final var request = objectMapper.treeToValue(jsonNode, HandleEventsRegistryRequest.class);
            return handleApiRequestOperation.handleApiRequest(log, request, webService::handleEvents);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_REQUEST, e.getMessage(), e);
        }
    }

    @Override
    @PermitAll
    public Uni<BasicAuthRegistryResponse> basicAuth(@NotNull @BeanParam final BasicAuthRegistryRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::basicAuth);
    }

    @Override
    @PermitAll
    public Uni<OAuth2RegistryResponse> oAuth2(@NotNull @BeanParam OAuth2RegistryRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::oAuth2);
    }
}

