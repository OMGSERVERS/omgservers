package com.omgservers.service.module.root.impl.service.rootService.impl.method.root.getRoot;

import com.omgservers.schema.module.root.root.GetRootRequest;
import com.omgservers.schema.module.root.root.GetRootResponse;
import com.omgservers.service.module.root.impl.operation.root.selectRoot.SelectRootOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRootMethodImpl implements GetRootMethod {

    final SelectRootOperation selectRootOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRootResponse> getRoot(final GetRootRequest request) {
        log.debug("Get root, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRootOperation
                            .selectRoot(sqlConnection, shard.shard(), id));
                })
                .map(GetRootResponse::new);
    }
}
