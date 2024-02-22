package com.omgservers.service.module.lobby.impl.service.lobbyService.impl.method.findLobbyRuntimeRef;

import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeRefResponse;
import com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntimeRefByLobbyId.SelectLobbyRuntimeByLobbyIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindLobbyRuntimeRefMethodImpl implements FindLobbyRuntimeRefMethod {

    final SelectLobbyRuntimeByLobbyIdOperation selectLobbyRuntimeByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindLobbyRuntimeRefResponse> findLobbyRuntimeRef(final FindLobbyRuntimeRefRequest request) {
        log.debug("Find lobby runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectLobbyRuntimeByLobbyIdOperation
                            .selectLobbyRuntimeRefByLobbyId(sqlConnection,
                                    shard.shard(),
                                    lobbyId));
                })
                .map(FindLobbyRuntimeRefResponse::new);
    }
}
