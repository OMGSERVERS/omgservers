package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.viewRootEntityRefs;

import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsResponse;
import com.omgservers.service.module.root.impl.operation.rootEntityRef.selectActiveRootEntityRefByRootId.SelectActiveRootEntityRefByRootIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(
            final ViewRootEntityRefsRequest request) {
        log.debug("View root entity refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var poolId = request.getRootId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveRootEntityRefByRootIdOperation
                                    .selectActiveRootEntityRefByRootId(sqlConnection,
                                            shard.shard(),
                                            poolId));
                })
                .map(ViewRootEntityRefsResponse::new);
    }
}
