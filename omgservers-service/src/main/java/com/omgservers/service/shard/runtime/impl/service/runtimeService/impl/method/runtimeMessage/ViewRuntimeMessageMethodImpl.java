package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.SelectActiveRuntimeMessagesByRuntimeIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeMessageMethodImpl implements ViewRuntimeMessageMethod {

    final SelectActiveRuntimeMessagesByRuntimeIdOperation selectActiveRuntimeMessagesByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeMessagesResponse> execute(ViewRuntimeMessagesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeMessagesByRuntimeIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId));
                })
                .map(ViewRuntimeMessagesResponse::new);

    }
}
