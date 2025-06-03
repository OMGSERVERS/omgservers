package com.omgservers.service.entrypoint.connector.impl.service.webService.impl;

import com.omgservers.api.ConnectorApi;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.connector.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.PLAYER})
class ConnectorApiImpl implements ConnectorApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenConnectorResponse> execute(@NotNull final CreateTokenConnectorRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.CONNECTOR})
    public Uni<InterchangeMessagesConnectorResponse> execute(
            @NotNull final InterchangeMessagesConnectorRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
