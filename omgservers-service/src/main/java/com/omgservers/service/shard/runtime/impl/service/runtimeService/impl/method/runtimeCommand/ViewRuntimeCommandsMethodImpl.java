package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(final ShardModel shardModel,
                                                    final ViewRuntimeCommandsRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeCommandsByRuntimeIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                runtimeId))
                .map(ViewRuntimeCommandsResponse::new);

    }
}
