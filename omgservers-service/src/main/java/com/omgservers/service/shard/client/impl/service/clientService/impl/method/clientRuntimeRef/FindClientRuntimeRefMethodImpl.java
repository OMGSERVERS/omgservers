package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.SelectClientRuntimeRefByClientIdAndRuntimeIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindClientRuntimeRefMethodImpl implements FindClientRuntimeRefMethod {

    final SelectClientRuntimeRefByClientIdAndRuntimeIdOperation selectClientRuntimeRefByClientIdAndRuntimeIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindClientRuntimeRefResponse> execute(final ShardModel shardModel,
                                                     final FindClientRuntimeRefRequest request) {
        log.debug("{}", request);

        final var clientId = request.getClientId();
        final var runtimeId = request.getRuntimeId();
        return pgPool.withTransaction(sqlConnection -> selectClientRuntimeRefByClientIdAndRuntimeIdOperation
                        .selectClientRuntimeRefByClientIdAndRuntimeId(sqlConnection,
                                shardModel.slot(),
                                clientId,
                                runtimeId))
                .map(FindClientRuntimeRefResponse::new);
    }
}
