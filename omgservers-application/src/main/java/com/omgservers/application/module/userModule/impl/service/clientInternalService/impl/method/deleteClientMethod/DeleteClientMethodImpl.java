package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod;

import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.userModule.impl.operation.deleteClientOperation.DeleteClientOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteClientMethodImpl implements DeleteClientMethod {

    final CheckShardOperation checkShardOperation;
    final DeleteClientOperation deleteClientOperation;
    final PgPool pgPool;

    @Override
    public Uni<Void> deleteClient(final DeleteClientInternalRequest request) {
        DeleteClientInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var client = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> deleteClientOperation
                                    .deleteClient(sqlConnection, shard, client))
                            .replaceWithVoid();
                });
    }
}
