package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.SelectRuntimeAssignmentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(final GetRuntimeAssignmentRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeAssignmentOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimeAssignmentResponse::new);
    }
}
