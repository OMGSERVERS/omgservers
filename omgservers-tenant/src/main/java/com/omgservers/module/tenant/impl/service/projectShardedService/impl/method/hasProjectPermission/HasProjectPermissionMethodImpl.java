package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.hasProjectPermission;

import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.module.tenant.impl.operation.hasProjectPermission.HasProjectPermissionOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenant.HasProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasProjectPermissionMethodImpl implements HasProjectPermissionMethod {

    final HasProjectPermissionOperation hasProjectPermissionOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardedRequest request) {
        HasProjectPermissionShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var projectId = request.getProjectId();
                    final var userId = request.getUserId();
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> hasProjectPermissionOperation
                            .hasProjectPermission(sqlConnection, shard.shard(), projectId, userId, permission));
                })
                .map(HasProjectPermissionInternalResponse::new);
    }
}
