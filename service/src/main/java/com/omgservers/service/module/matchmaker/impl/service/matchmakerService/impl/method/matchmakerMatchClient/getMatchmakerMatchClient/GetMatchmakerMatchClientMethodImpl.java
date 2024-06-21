package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchClient.getMatchmakerMatchClient;

import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.selectMatchmakerMatchClient.SelectMatchmakerMatchClientOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMatchClientMethodImpl implements GetMatchmakerMatchClientMethod {

    final SelectMatchmakerMatchClientOperation selectMatchmakerMatchClientOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchClientResponse> getMatchmakerMatchClient(
            final GetMatchmakerMatchClientRequest request) {
        log.debug("Get match client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchClientOperation
                            .selectMatchmakerMatchClient(sqlConnection, shard.shard(), matchmakerId, id));
                })
                .map(GetMatchmakerMatchClientResponse::new);
    }
}
