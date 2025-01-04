package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePermission;

import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePermission.SelectActiveRuntimePermissionsByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimePermissionsMethodImpl implements ViewRuntimePermissionsMethod {

    final SelectActiveRuntimePermissionsByRuntimeIdOperation selectActiveRuntimePermissionsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimePermissionsResponse> execute(final ViewRuntimePermissionsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimePermissionsByRuntimeIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(ViewRuntimePermissionsResponse::new);
    }
}
