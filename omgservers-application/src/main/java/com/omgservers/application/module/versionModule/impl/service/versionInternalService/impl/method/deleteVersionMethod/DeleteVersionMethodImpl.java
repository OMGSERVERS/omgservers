package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod;

import com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation.DeleteVersionOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.DeleteVersionInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final CheckShardOperation checkShardOperation;
    final DeleteVersionOperation deleteVersionOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteVersion(final DeleteVersionInternalRequest request) {
        DeleteVersionInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var uuid = request.getUuid();
                    return pgPool.withTransaction(sqlConnection -> deleteVersionOperation
                                    .deleteVersion(sqlConnection, shard, uuid))
                            .replaceWithVoid();
                });
    }
}
