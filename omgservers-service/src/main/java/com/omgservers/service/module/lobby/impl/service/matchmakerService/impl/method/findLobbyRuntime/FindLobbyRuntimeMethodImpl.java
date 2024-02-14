package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.findLobbyRuntime;

import com.omgservers.model.dto.lobby.FindLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.FindLobbyRuntimeResponse;
import com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntimeByLobbyIdAndRuntimeId.SelectLobbyRuntimeByLobbyIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindLobbyRuntimeMethodImpl implements FindLobbyRuntimeMethod {

    final SelectLobbyRuntimeByLobbyIdAndRuntimeIdOperation selectLobbyRuntimeByLobbyIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindLobbyRuntimeResponse> findLobbyRuntime(final FindLobbyRuntimeRequest request) {
        log.debug("Find lobby runtime, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getLobbyId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectLobbyRuntimeByLobbyIdAndRuntimeIdOperation
                            .selectLobbyRuntimeByLobbyIdAndRuntimeId(sqlConnection,
                                    shard.shard(),
                                    lobbyId,
                                    runtimeId));
                })
                .map(FindLobbyRuntimeResponse::new);
    }
}
