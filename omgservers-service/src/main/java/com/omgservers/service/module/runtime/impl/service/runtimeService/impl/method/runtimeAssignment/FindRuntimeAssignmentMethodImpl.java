package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.SelectRuntimeAssignmentByRuntimeIdAndClientIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimeAssignmentMethodImpl implements FindRuntimeAssignmentMethod {

    final SelectRuntimeAssignmentByRuntimeIdAndClientIdOperation selectRuntimeAssignmentByRuntimeIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(final FindRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var entityId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeAssignmentByRuntimeIdAndClientIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    entityId));
                })
                .map(FindRuntimeAssignmentResponse::new);
    }
}
