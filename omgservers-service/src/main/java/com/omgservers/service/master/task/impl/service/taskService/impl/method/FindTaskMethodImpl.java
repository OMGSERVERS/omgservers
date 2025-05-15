package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.FindTaskRequest;
import com.omgservers.schema.master.task.FindTaskResponse;
import com.omgservers.service.master.task.impl.operation.SelectTaskByEntityIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class FindTaskMethodImpl implements FindTaskMethod {

    final SelectTaskByEntityIdOperation selectTaskByEntityIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindTaskResponse> execute(final FindTaskRequest request) {
        log.debug("{}", request);

        final var shard_key = request.getShardKey();
        final var entityId = request.getEntityId();
        return pgPool.withTransaction(sqlConnection -> selectTaskByEntityIdOperation
                        .execute(sqlConnection, shard_key, entityId))
                .map(FindTaskResponse::new);
    }
}
