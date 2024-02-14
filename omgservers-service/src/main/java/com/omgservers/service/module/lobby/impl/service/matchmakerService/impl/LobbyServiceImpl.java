package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl;

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
import com.omgservers.service.module.lobby.impl.operation.getLobbyModuleClient.GetLobbyModuleClientOperation;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.deleteLobby.DeleteLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.deleteLobbyRuntime.DeleteLobbyRuntimeMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.findLobbyRuntime.FindLobbyRuntimeMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.getLobby.GetLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.getLobbyRuntime.GetLobbyRuntimeMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.syncLobby.SyncLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.syncLobbyRuntime.SyncLobbyRuntimeMethod;
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

    final DeleteLobbyRuntimeMethod deleteLobbyRuntimeMethod;
    final FindLobbyRuntimeMethod findLobbyRuntimeMethod;
    final SyncLobbyRuntimeMethod syncLobbyRuntimeMethod;
    final GetLobbyRuntimeMethod getLobbyRuntimeMethod;

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
    public Uni<GetLobbyRuntimeResponse> getLobbyRuntime(@Valid final GetLobbyRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::getLobbyRuntime,
                getLobbyRuntimeMethod::getLobbyRuntime);
    }

    @Override
    public Uni<FindLobbyRuntimeResponse> findLobbyRuntime(@Valid final FindLobbyRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::findLobbyRuntime,
                findLobbyRuntimeMethod::findLobbyRuntime);
    }

    @Override
    public Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(@Valid final SyncLobbyRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::syncLobbyRuntime,
                syncLobbyRuntimeMethod::syncLobbyRuntime);
    }

    @Override
    public Uni<DeleteLobbyRuntimeResponse> deleteLobbyRuntime(@Valid final DeleteLobbyRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::deleteLobbyRuntime,
                deleteLobbyRuntimeMethod::deleteLobbyRuntime);
    }
}
