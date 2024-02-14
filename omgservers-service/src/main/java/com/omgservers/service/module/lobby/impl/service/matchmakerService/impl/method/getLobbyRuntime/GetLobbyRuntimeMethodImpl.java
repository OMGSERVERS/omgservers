package com.omgservers.service.module.lobby.impl.service.matchmakerService.impl.method.getLobbyRuntime;

import com.omgservers.model.dto.lobby.GetLobbyRuntimeRequest;
import com.omgservers.model.dto.lobby.GetLobbyRuntimeResponse;
import com.omgservers.service.module.lobby.impl.operation.selectLobbyRuntime.SelectLobbyRuntimeOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetLobbyRuntimeMethodImpl implements GetLobbyRuntimeMethod {

    final SelectLobbyRuntimeOperation selectLobbyRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetLobbyRuntimeResponse> getLobbyRuntime(final GetLobbyRuntimeRequest request) {
        log.debug("Get lobby runtime, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var lobbyId = request.getLobbyId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectLobbyRuntimeOperation.selectLobbyRuntime(sqlConnection,
                                    shard.shard(),
                                    lobbyId,
                                    id));
                })
                .map(GetLobbyRuntimeResponse::new);
    }
}
