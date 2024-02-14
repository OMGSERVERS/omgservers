package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.getLobby;

import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.service.module.lobby.impl.operation.selectLobby.SelectLobbyOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetLobbyResponse> getLobby(final GetLobbyRequest request) {
        log.debug("Get lobby, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectLobbyOperation
                            .selectLobby(sqlConnection, shard.shard(), id));
                })
                .map(GetLobbyResponse::new);
    }
}
