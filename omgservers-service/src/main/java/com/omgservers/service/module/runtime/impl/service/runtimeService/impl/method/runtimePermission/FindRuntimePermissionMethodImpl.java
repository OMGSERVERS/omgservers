package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePermission.SelectRuntimePermissionByRuntimeIdAndUserIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimePermissionMethodImpl implements FindRuntimePermissionMethod {

    final SelectRuntimePermissionByRuntimeIdAndUserIdOperation selectRuntimePermissionByRuntimeIdAndUserIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimePermissionResponse> execute(final FindRuntimePermissionRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimePermissionByRuntimeIdAndUserIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    userId,
                                    permission));
                })
                .map(FindRuntimePermissionResponse::new);
    }
}
