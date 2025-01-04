package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobby.syncLobby;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.service.module.lobby.impl.operation.lobby.upsertLobby.UpsertLobbyOperation;
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
class SyncLobbyMethodImpl implements SyncLobbyMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertLobbyOperation upsertLobbyOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncLobbyResponse> syncLobby(final SyncLobbyRequest request) {
        log.trace("{}", request);

        final var lobby = request.getLobby();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertLobbyOperation.upsertLobby(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            lobby))
                            .map(ChangeContext::getResult);
                })
                .map(SyncLobbyResponse::new);
    }
}
