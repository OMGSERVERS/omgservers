package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment.viewRuntimeAssignments;

import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.selectActiveRuntimeAssignmentsByRuntimeId.SelectActiveRuntimeAssignmentsByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(final ViewRuntimeAssignmentsRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeAssignmentsByRuntimeIdOperation
                            .selectActiveRuntimeAssignmentsByRuntimeId(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(ViewRuntimeAssignmentsResponse::new);
    }
}
