package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.match.impl.operation.match.SelectMatchOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchMethodImpl implements GetMatchMethod {

    final SelectMatchOperation selectMatchOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchResponse> execute(final GetMatchRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetMatchResponse::new);
    }
}
