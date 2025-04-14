package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.SelectActiveRuntimeAssignmentsByRuntimeIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeAssignmentsMethodImpl implements ViewRuntimeAssignmentsMethod {

    final SelectActiveRuntimeAssignmentsByRuntimeIdOperation selectActiveRuntimeAssignmentsByRuntimeIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> execute(final ShardModel shardModel,
                                                       final ViewRuntimeAssignmentsRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeAssignmentsByRuntimeIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                runtimeId))
                .map(ViewRuntimeAssignmentsResponse::new);
    }
}
