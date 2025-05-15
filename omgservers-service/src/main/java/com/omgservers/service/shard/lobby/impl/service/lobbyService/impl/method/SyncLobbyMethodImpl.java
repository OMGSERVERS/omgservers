package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.lobby.SyncLobbyRequest;
import com.omgservers.schema.shard.lobby.SyncLobbyResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<SyncLobbyResponse> execute(final ShardModel shardModel,
                                          final SyncLobbyRequest request) {
        log.debug("{}", request);

        final var lobby = request.getLobby();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertLobbyOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                lobby))
                .map(ChangeContext::getResult)
                .map(SyncLobbyResponse::new);
    }
}
