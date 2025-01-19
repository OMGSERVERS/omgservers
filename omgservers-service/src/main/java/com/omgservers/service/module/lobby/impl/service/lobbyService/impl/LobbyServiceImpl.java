package com.omgservers.service.module.lobby.impl.service.lobbyService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.lobby.impl.operation.getLobbyModuleClient.GetLobbyModuleClientOperation;
import com.omgservers.service.module.lobby.impl.service.lobbyService.LobbyService;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobby.deleteLobby.DeleteLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobby.getLobby.GetLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobby.syncLobby.SyncLobbyMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.deleteLobbyRuntimeRef.DeleteLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.findLobbyRuntimeRef.FindLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.getLobbyRuntimeRef.GetLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.syncLobbyRuntimeRef.SyncLobbyRuntimeRefMethod;
import com.omgservers.service.module.lobby.impl.service.webService.impl.api.LobbyApi;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
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
    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<GetLobbyResponse> getLobby(@Valid final GetLobbyRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::getLobby,
                getLobbyMethod::getLobby);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobby(@Valid final SyncLobbyRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::syncLobby,
                syncLobbyMethod::syncLobby);
    }

    @Override
    public Uni<SyncLobbyResponse> syncLobbyWithIdempotency(SyncLobbyRequest request) {
        return syncLobby(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", request.getLobby(), t.getMessage());
                            return Uni.createFrom().item(new SyncLobbyResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteLobbyResponse> deleteLobby(@Valid final DeleteLobbyRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::deleteLobby,
                deleteLobbyMethod::deleteLobby);
    }

    @Override
    public Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(@Valid final GetLobbyRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::getLobbyRuntimeRef,
                getLobbyRuntimeRefMethod::getLobbyRuntimeRef);
    }

    @Override
    public Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(@Valid final FindLobbyRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::findLobbyRuntimeRef,
                findLobbyRuntimeRefMethod::findLobbyRuntimeRef);
    }

    @Override
    public Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(@Valid final SyncLobbyRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::syncLobbyRuntimeRef,
                syncLobbyRuntimeRefMethod::syncLobbyRuntimeRef);
    }

    @Override
    public Uni<DeleteLobbyRuntimeRefResponse> deleteLobbyRuntimeRef(@Valid final DeleteLobbyRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::deleteLobbyRuntimeRef,
                deleteLobbyRuntimeRefMethod::deleteLobbyRuntimeRef);
    }
}
