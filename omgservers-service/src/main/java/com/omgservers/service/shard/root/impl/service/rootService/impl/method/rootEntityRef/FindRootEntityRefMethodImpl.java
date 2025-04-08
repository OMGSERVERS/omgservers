package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.SelectRootEntityRefByRootIdAndEntityIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRootEntityRefMethodImpl implements FindRootEntityRefMethod {

    final SelectRootEntityRefByRootIdAndEntityIdOperation selectRootEntityRefByRootIdAndEntityIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRootEntityRefResponse> execute(final ShardModel shardModel,
                                                  final FindRootEntityRefRequest request) {
        log.trace("{}", request);

        final var rootId = request.getRootId();
        final var entityId = request.getEntityId();
        return pgPool.withTransaction(sqlConnection -> selectRootEntityRefByRootIdAndEntityIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                rootId,
                                entityId))
                .map(FindRootEntityRefResponse::new);
    }
}
