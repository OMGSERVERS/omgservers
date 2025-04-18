package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.lobby.DeleteLobbyRequest;
import com.omgservers.schema.shard.lobby.DeleteLobbyResponse;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.lobby.impl.operation.getLobbyModuleClient.GetLobbyModuleClientOperation;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.LobbyService;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.DeleteLobbyMethod;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.GetLobbyMethod;
import com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method.SyncLobbyMethod;
import com.omgservers.service.shard.lobby.impl.service.webService.impl.api.LobbyApi;
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

    final DeleteLobbyMethod deleteLobbyMethod;
    final SyncLobbyMethod syncLobbyMethod;
    final GetLobbyMethod getLobbyMethod;

    final GetLobbyModuleClientOperation getMatchServiceApiClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;

    @Override
    public Uni<GetLobbyResponse> execute(@Valid final GetLobbyRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::execute,
                getLobbyMethod::execute);
    }

    @Override
    public Uni<SyncLobbyResponse> execute(@Valid final SyncLobbyRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::execute,
                syncLobbyMethod::execute);
    }

    @Override
    public Uni<SyncLobbyResponse> executeWithIdempotency(SyncLobbyRequest request) {
        return execute(request)
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
    public Uni<DeleteLobbyResponse> execute(@Valid final DeleteLobbyRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getMatchServiceApiClientOperation::getClient,
                LobbyApi::execute,
                deleteLobbyMethod::execute);
    }
}
