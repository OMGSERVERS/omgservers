package com.omgservers.service.module.lobby.impl.service.webService.impl.api;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.lobby.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LobbyApiImpl implements LobbyApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetLobbyResponse> getLobby(final GetLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getLobby);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncLobbyResponse> syncLobby(final SyncLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobby);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteLobbyResponse> deleteLobby(final DeleteLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteLobby);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetLobbyRuntimeResponse> getLobbyRuntime(final GetLobbyRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getLobbyRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindLobbyRuntimeResponse> findLobbyRuntime(final FindLobbyRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findLobbyRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(final SyncLobbyRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobbyRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(final DeleteLobbyRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteLobbyRuntime);
    }
}
