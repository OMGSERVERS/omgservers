package com.omgservers.service.shard.lobby.impl.service.lobbyService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.lobby.GetLobbyRequest;
import com.omgservers.schema.shard.lobby.GetLobbyResponse;
import com.omgservers.service.shard.lobby.impl.operation.lobby.SelectLobbyOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetLobbyMethodImpl implements GetLobbyMethod {

    final SelectLobbyOperation selectLobbyOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetLobbyResponse> execute(final ShardModel shardModel,
                                         final GetLobbyRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectLobbyOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetLobbyResponse::new);
    }
}
