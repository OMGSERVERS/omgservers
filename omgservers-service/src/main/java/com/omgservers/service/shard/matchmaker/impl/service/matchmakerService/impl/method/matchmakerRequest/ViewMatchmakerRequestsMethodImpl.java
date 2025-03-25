package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerRequest;

import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.ViewMatchmakerRequestsResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerRequest.SelectActiveMatchmakerRequestsByMatchmakerIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerRequestsMethodImpl implements ViewMatchmakerRequestsMethod {

    final SelectActiveMatchmakerRequestsByMatchmakerIdOperation selectActiveMatchmakerRequestsByMatchmakerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerRequestsResponse> execute(ViewMatchmakerRequestsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var matchmakerId = request.getMatchmakerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerRequestsByMatchmakerIdOperation
                            .execute(sqlConnection, shard.shard(), matchmakerId));
                })
                .map(ViewMatchmakerRequestsResponse::new);

    }
}
