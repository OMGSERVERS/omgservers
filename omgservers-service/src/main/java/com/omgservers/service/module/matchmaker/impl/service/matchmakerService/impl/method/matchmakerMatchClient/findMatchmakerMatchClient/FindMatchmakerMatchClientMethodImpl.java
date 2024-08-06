package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.findMatchmakerMatchClient;

import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.FindMatchmakerMatchClientResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClientByMatchmakerIdAndClientId.SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindMatchmakerMatchClientMethodImpl implements FindMatchmakerMatchClientMethod {

    final SelectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation
            selectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindMatchmakerMatchClientResponse> findMatchmakerMatchClient(
            final FindMatchmakerMatchClientRequest request) {
        log.debug("Find matchmaker match client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectMatchmakerMatchClientByMatchmakerIdAndClientIdOperation
                                    .selectMatchmakerMatchClientByMatchmakerIdAndClientId(sqlConnection,
                                            shard.shard(),
                                            matchmakerId,
                                            clientId));
                })
                .map(FindMatchmakerMatchClientResponse::new);
    }
}
