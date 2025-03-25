package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.module.lobby.SyncLobbyRequest;
import com.omgservers.schema.module.lobby.SyncLobbyResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.lobby.impl.operation.lobby.UpsertLobbyOperation;
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
    public Uni<SyncLobbyResponse> execute(final SyncLobbyRequest request) {
        log.trace("{}", request);

        final var lobby = request.getLobby();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> upsertLobbyOperation.execute(
                                            changeContext,
                                            sqlConnection,
                                            shard,
                                            lobby))
                            .map(ChangeContext::getResult);
                })
                .map(SyncLobbyResponse::new);
    }
}
