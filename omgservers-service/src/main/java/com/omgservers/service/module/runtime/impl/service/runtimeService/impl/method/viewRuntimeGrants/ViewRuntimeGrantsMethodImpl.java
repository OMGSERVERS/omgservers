package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeGrants;

import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeGrantsByRuntimeId.SelectActiveRuntimeGrantsByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeGrantsMethodImpl implements ViewRuntimeGrantsMethod {

    final SelectActiveRuntimeGrantsByRuntimeIdOperation selectActiveRuntimeGrantsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeGrantsResponse> viewRuntimeGrants(final ViewRuntimeGrantsRequest request) {
        log.debug("View runtime grants, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeGrantsByRuntimeIdOperation
                            .selectActiveRuntimeGrantsByRuntimeId(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(ViewRuntimeGrantsResponse::new);
    }
}
