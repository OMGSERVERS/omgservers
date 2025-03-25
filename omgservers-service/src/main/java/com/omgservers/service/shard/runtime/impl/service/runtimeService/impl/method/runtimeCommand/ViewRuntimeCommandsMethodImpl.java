package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.SelectActiveRuntimeCommandsByRuntimeIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeCommandsMethodImpl implements ViewRuntimeCommandsMethod {

    final SelectActiveRuntimeCommandsByRuntimeIdOperation selectActiveRuntimeCommandsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(final ViewRuntimeCommandsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeCommandsByRuntimeIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId));
                })
                .map(ViewRuntimeCommandsResponse::new);

    }
}
