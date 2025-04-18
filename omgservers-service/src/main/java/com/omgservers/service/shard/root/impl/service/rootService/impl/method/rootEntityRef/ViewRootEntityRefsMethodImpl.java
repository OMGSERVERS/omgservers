package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.shard.root.rootEntityRef.ViewRootEntityRefsResponse;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.SelectActiveRootEntityRefByRootIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRootEntityRefsMethodImpl implements ViewRootEntityRefsMethod {

    final SelectActiveRootEntityRefByRootIdOperation selectActiveRootEntityRefByRootIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRootEntityRefsResponse> execute(final ShardModel shardModel,
                                                   final ViewRootEntityRefsRequest request) {
        log.trace("{}", request);

        final var poolId = request.getRootId();
        return pgPool.withTransaction(sqlConnection -> selectActiveRootEntityRefByRootIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                poolId))
                .map(ViewRootEntityRefsResponse::new);
    }
}
