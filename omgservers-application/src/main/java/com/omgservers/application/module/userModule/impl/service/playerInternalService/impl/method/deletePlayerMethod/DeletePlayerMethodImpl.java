package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod;

import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.operation.deletePlayerOperation.DeletePlayerOperation;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePlayerMethodImpl implements DeletePlayerMethod {

    final CheckShardOperation checkShardOperation;
    final DeletePlayerOperation deletePlayerOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> deletePlayer(final DeletePlayerInternalRequest request) {
        DeletePlayerInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> deletePlayerOperation
                                    .deletePlayer(sqlConnection, shard, id))
                            .replaceWithVoid();
                });
    }
}
