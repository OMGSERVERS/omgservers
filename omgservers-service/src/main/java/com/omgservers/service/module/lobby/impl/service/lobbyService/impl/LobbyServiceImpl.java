package com.omgservers.service.module.lobby.impl.service.lobbyService.impl;

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
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.service.module.lobby.impl.operation.getLobbyModuleClient.GetLobbyModuleClientOperation;
import com.omgservers.service.module.lobby.impl.service.lobbyService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.deleteLobby.DeleteLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.deleteLobbyRuntimeRef.DeleteLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.findLobbyRuntimeRef.FindLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.getLobby.GetLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.getLobbyRuntimeRef.GetLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.syncLobby.SyncLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.syncLobbyRuntimeRef.SyncLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.webService.impl.api.LobbyApi;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class LobbyServiceImpl implements LobbyService {

    final DeleteLobbyRuntimeRefMethod deleteLobbyRuntimeRefMethod;
    final FindLobbyRuntimeRefMethod findLobbyRuntimeRefMethod;
    final SyncLobbyRuntimeRefMethod syncLobbyRuntimeRefMethod;
    final GetLobbyRuntimeRefMethod getLobbyRuntimeRefMethod;
    final DeleteLobbyMethod deleteLobbyMethod;
    final SyncLobbyMethod syncLobbyMethod;
    final GetLobbyMethod getLobbyMethod;

    final GetLobbyModuleClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetLobbyResponse> getLobby(@Valid final GetLobbyRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::getLobby,
                getLobbyMethod::getLobby);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobby(@Valid final SyncLobbyRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::syncLobby,
                syncLobbyMethod::syncLobby);
    }

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(@Valid final DeleteLobbyRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::deleteLobby,
                deleteLobbyMethod::deleteLobby);
    }

    @Override
    public Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(@Valid final GetLobbyRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::getLobbyRuntimeRef,
                getLobbyRuntimeRefMethod::getLobbyRuntimeRef);
    }

    @Override
    public Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(@Valid final FindLobbyRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::findLobbyRuntimeRef,
                findLobbyRuntimeRefMethod::findLobbyRuntimeRef);
    }

    @Override
    public Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(@Valid final SyncLobbyRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::syncLobbyRuntimeRef,
                syncLobbyRuntimeRefMethod::syncLobbyRuntimeRef);
    }

    @Override
    public Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(@Valid final DeleteLobbyRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::deleteLobbyRuntimeRef,
                deleteLobbyRuntimeRefMethod::deleteLobbyRuntimeRef);
    }
}
