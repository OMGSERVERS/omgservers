package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.lobbyRuntimeRef.getLobbyRuntimeRef;

import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefRequest;
import com.omgservers.schema.module.lobby.GetLobbyRuntimeRefResponse;
import com.omgservers.service.module.lobby.impl.operation.lobbyRuntimeRef.selectLobbyRuntimeRef.SelectLobbyRuntimeRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetLobbyRuntimeRefMethodImpl implements GetLobbyRuntimeRefMethod {

    final SelectLobbyRuntimeRefOperation selectLobbyRuntimeRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetLobbyRuntimeRefResponse> getLobbyRuntimeRef(final GetLobbyRuntimeRefRequest request) {
        log.debug("Get lobby runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getLobbyId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectLobbyRuntimeRefOperation
                            .selectLobbyRuntimeRef(sqlConnection, shard.shard(), lobbyId, id));
                })
                .map(GetLobbyRuntimeRefResponse::new);
    }
}
