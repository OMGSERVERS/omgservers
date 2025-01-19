package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.service.module.matchmaker.impl.operation.matchmaker.SelectMatchmakerOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMethodImpl implements GetMatchmakerMethod {

    final SelectMatchmakerOperation selectMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerResponse> execute(final GetMatchmakerRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetMatchmakerResponse::new);
    }
}
