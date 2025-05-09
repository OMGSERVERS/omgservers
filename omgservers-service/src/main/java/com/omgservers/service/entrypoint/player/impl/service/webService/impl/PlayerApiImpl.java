package com.omgservers.service.entrypoint.player.impl.service.webService.impl;

import com.omgservers.api.PlayerApi;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateUserPlayerResponse;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerResponse;
import com.omgservers.schema.entrypoint.player.PingServicePlayerRequest;
import com.omgservers.schema.entrypoint.player.PingServicePlayerResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.player.impl.service.webService.WebService;
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
class PlayerApiImpl implements PlayerApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<PingServicePlayerResponse> execute(@NotNull final PingServicePlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @PermitAll
    public Uni<CreateUserPlayerResponse> execute(@NotNull final CreateUserPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @PermitAll
    public Uni<CreateTokenPlayerResponse> execute(@NotNull final CreateTokenPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.PLAYER})
    public Uni<CreateClientPlayerResponse> execute(@NotNull final CreateClientPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.PLAYER})
    public Uni<InterchangeMessagesPlayerResponse> execute(@NotNull final InterchangeMessagesPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
