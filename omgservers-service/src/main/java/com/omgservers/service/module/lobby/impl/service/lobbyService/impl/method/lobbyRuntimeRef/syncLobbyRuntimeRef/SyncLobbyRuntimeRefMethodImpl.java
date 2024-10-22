package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.syncLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.SyncLobbyRuntimeRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.lobby.impl.operation.lobby.hasLobby.HasLobbyOperation;
import com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.upsertLobbyRuntimeRef.UpsertLobbyRuntimeRefOperation;
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
class SyncLobbyRuntimeRefMethodImpl implements SyncLobbyRuntimeRefMethod {

    final UpsertLobbyRuntimeRefOperation upsertLobbyRuntimeRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasLobbyOperation hasLobbyOperation;

    @Override
    public Uni<SyncLobbyRuntimeRefResponse> syncLobbyRuntimeRef(SyncLobbyRuntimeRefRequest request) {
        log.debug("Requested, {}", request);

        final var shardKey = request.getRequestShardKey();
        final var lobbyRuntime = request.getLobbyRuntimeRef();
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
                                                    return upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(context,
                                                            sqlConnection,
                                                            shard,
                                                            lobbyRuntime);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "lobby does not exist or was deleted, id=" + lobbyId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncLobbyRuntimeRefResponse::new);
    }
}
