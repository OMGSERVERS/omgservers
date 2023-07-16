package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.deleteProjectMethod;

import com.omgservers.application.module.tenantModule.impl.operation.deleteProjectOperation.DeleteProjectOperation;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.DeleteProjectInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final DeleteProjectOperation deleteProjectOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteProject(final DeleteProjectInternalRequest request) {
        DeleteProjectInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var uuid = request.getUuid();
                    return pgPool.withTransaction(sqlConnection -> deleteProjectOperation
                                    .deleteProject(sqlConnection, shard.shard(), uuid))
                            .replaceWithVoid();
                });
    }
}
