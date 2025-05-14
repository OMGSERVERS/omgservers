package com.omgservers.service.master.task.impl.service.taskService.impl.method;

import com.omgservers.schema.master.task.GetTaskRequest;
import com.omgservers.schema.master.task.GetTaskResponse;
import com.omgservers.service.master.task.impl.operation.SelectTaskOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetTaskMethodImpl implements GetTaskMethod {

    final SelectTaskOperation selectTaskOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTaskResponse> execute(final GetTaskRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTaskOperation
                        .execute(sqlConnection, id))
                .map(GetTaskResponse::new);
    }
}
