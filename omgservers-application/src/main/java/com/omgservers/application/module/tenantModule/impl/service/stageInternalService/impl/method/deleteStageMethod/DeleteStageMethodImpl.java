package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.application.module.tenantModule.impl.operation.deleteStageOperation.DeleteStageOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageMethodImpl implements DeleteStageMethod {

    final DeleteStageOperation deleteStageOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteStage(final DeleteStageInternalRequest request) {
        DeleteStageInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var uuid = request.getUuid();
                    return pgPool.withTransaction(sqlConnection -> deleteStageOperation
                            .deleteStage(sqlConnection, shard.shard(), uuid));
                })
                .replaceWithVoid();
    }
}
