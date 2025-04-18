package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.FindRuntimeAssignmentResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.SelectRuntimeAssignmentByRuntimeIdAndClientIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(final ShardModel shardModel,
                                                      final FindRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var entityId = request.getClientId();
        return pgPool.withTransaction(sqlConnection -> selectRuntimeAssignmentByRuntimeIdAndClientIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                runtimeId,
                                entityId))
                .map(FindRuntimeAssignmentResponse::new);
    }
}
