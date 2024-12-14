package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.getRootEntityRef;

import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.GetRootEntityRefResponse;
import com.omgservers.service.module.root.impl.operation.rootEntityRef.selectRootEntityRef.SelectRootEntityRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRootEntityRefMethodImpl implements GetRootEntityRefMethod {

    final SelectRootEntityRefOperation selectRootEntityRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRootEntityRefResponse> getRootEntityRef(
            final GetRootEntityRefRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var rootId = request.getRootId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRootEntityRefOperation
                            .selectRootEntityRef(sqlConnection, shard.shard(), rootId, id));
                })
                .map(GetRootEntityRefResponse::new);
    }
}
