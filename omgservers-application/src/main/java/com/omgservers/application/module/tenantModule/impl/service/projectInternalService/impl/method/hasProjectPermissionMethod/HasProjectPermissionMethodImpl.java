package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.hasProjectPermissionMethod;

import com.omgservers.application.module.tenantModule.impl.operation.hasProjectPermissionOperation.HasProjectPermissionOperation;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
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
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request) {
        HasProjectPermissionInternalRequest.validate(request);

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
