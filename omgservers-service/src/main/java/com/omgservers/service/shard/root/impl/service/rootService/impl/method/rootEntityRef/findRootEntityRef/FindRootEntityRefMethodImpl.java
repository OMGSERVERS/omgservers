package com.omgservers.service.shard.root.impl.service.rootService.impl.method.rootEntityRef.findRootEntityRef;

import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.service.shard.root.impl.operation.rootEntityRef.selectRootEntityRefByRootIdAndEntityId.SelectRootEntityRefByRootIdAndEntityIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRootEntityRefResponse> findRootEntityRef(
            final FindRootEntityRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var rootId = request.getRootId();
                    final var entityId = request.getEntityId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectRootEntityRefByRootIdAndEntityIdOperation
                                    .selectRootEntityRefByRootIdAndEntityId(sqlConnection,
                                            shard.shard(),
                                            rootId,
                                            entityId));
                })
                .map(FindRootEntityRefResponse::new);
    }
}
