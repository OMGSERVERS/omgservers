package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntimeAssignment;

import com.omgservers.model.dto.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeAssignment.SelectRuntimeAssignmentOperation;
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
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(final GetRuntimeAssignmentRequest request) {
        log.debug("Get runtime assignment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeAssignmentOperation
                            .selectRuntimeAssignment(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    id));
                })
                .map(GetRuntimeAssignmentResponse::new);
    }
}
