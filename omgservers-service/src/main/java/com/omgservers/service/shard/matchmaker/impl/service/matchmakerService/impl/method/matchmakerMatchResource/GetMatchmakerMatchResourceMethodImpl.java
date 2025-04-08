package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceRequest;
import com.omgservers.schema.module.matchmaker.matchmakerMatchResource.GetMatchmakerMatchResourceResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.SelectMatchmakerMatchResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetMatchmakerMatchResourceMethodImpl implements GetMatchmakerMatchResourceMethod {

    final SelectMatchmakerMatchResourceOperation selectMatchmakerMatchResourceOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetMatchmakerMatchResourceResponse> execute(final ShardModel shardModel,
                                                           final GetMatchmakerMatchResourceRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectMatchmakerMatchResourceOperation
                        .execute(sqlConnection, shardModel.shard(), matchmakerId, id))
                .map(GetMatchmakerMatchResourceResponse::new);
    }
}
