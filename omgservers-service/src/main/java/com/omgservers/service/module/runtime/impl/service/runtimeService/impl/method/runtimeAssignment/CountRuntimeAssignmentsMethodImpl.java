package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeAssignment;

import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.CountRuntimeAssignmentsOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CountRuntimeAssignmentsMethodImpl implements CountRuntimeAssignmentsMethod {

    final CountRuntimeAssignmentsOperation countRuntimeAssignmentsOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<CountRuntimeAssignmentsResponse> execute(final CountRuntimeAssignmentsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();

                    return pgPool.withTransaction(sqlConnection -> countRuntimeAssignmentsOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(CountRuntimeAssignmentsResponse::new);
    }
}
