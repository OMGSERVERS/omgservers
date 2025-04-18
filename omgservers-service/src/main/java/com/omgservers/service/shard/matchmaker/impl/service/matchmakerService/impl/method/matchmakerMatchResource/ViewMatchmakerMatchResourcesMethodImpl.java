package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerMatchResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.ViewMatchmakerMatchResourcesResponse;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource.SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewMatchmakerMatchResourcesMethodImpl implements ViewMatchmakerMatchResourcesMethod {

    final SelectActiveMatchmakerMatchResourcesByMatchmakerIdOperation
            selectActiveMatchmakerMatchResourcesByMatchmakerIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewMatchmakerMatchResourcesResponse> execute(final ShardModel shardModel,
                                                             final ViewMatchmakerMatchResourcesRequest request) {
        log.trace("{}", request);

        final var matchmakerId = request.getMatchmakerId();
        return pgPool.withTransaction(sqlConnection -> selectActiveMatchmakerMatchResourcesByMatchmakerIdOperation
                        .execute(sqlConnection, shardModel.slot(), matchmakerId))
                .map(ViewMatchmakerMatchResourcesResponse::new);

    }
}
