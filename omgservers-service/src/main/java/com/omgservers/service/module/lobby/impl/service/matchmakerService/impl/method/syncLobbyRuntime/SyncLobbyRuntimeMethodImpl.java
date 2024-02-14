package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.syncLobbyRuntime;

import com.omgservers.model.dto.lobby.SyncLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.SyncLobbyRuntimeResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.lobby.impl.operation.hasLobby.HasLobbyOperation;
import com.omgservers.service.module.lobby.impl.operation.upsertLobbyRuntime.UpsertLobbyRuntimeOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncLobbyRuntimeMethodImpl implements SyncLobbyRuntimeMethod {

    final UpsertLobbyRuntimeOperation upsertLobbyRuntimeOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasLobbyOperation hasLobbyOperation;

    @Override
    public Uni<SyncLobbyRuntimeResponse> syncLobbyRuntime(SyncLobbyRuntimeRequest request) {
        log.debug("Sync lobby runtime, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var lobbyRuntime = request.getLobbyRuntime();
        final var lobbyId = lobbyRuntime.getLobbyId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (context, sqlConnection) -> hasLobbyOperation
                                            .hasLobby(sqlConnection, shard, lobbyId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertLobbyRuntimeOperation.upsertLobby(context,
                                                            sqlConnection,
                                                            shard,
                                                            lobbyRuntime);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "lobby does not exist or was deleted, " +
                                                                    "id=" + lobbyId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncLobbyRuntimeResponse::new);
    }
}
