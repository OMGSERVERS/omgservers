package com.omgservers.service.module.lobby.impl.service.webService.impl.api;

import com.omgservers.model.dto.lobby.DeleteLobbyRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyResponse;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRequest;
import com.omgservers.model.dto.lobby.SyncLobbyResponse;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.service.module.lobby.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
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
    public Uni<GetLobbyResponse> getLobby(final GetLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getLobby);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobby(final SyncLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobby);
    }

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(final DeleteLobbyRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteLobby);
    }

    @Override
    public Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(final GetLobbyRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getLobbyRuntimeRef);
    }

    @Override
    public Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(final FindLobbyRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findLobbyRuntimeRef);
    }

    @Override
    public Uni<SyncLobbyRuntimeResponse> syncLobbyRuntimeRef(final SyncLobbyRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobbyRuntimeRef);
    }

    @Override
    public Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(final DeleteLobbyRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteLobbyRuntimeRef);
    }
}
