package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.SelectRuntimeAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeAssignmentMethodImpl implements GetRuntimeAssignmentMethod {

    final SelectRuntimeAssignmentOperation selectRuntimeAssignmentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(final ShardModel shardModel,
                                                     final GetRuntimeAssignmentRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectRuntimeAssignmentOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                runtimeId,
                                id))
                .map(GetRuntimeAssignmentResponse::new);
    }
}
