package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.machmaker;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.GetMatchmakerResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.SelectMatchmakerOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerResponse> execute(final ShardModel shardModel,
                                              final GetMatchmakerRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetMatchmakerResponse::new);
    }
}
