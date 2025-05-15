package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.GetClientRuntimeRefResponse;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.SelectClientRuntimeRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientRuntimeRefMethodImpl implements GetClientRuntimeRefMethod {

    final SelectClientRuntimeRefOperation selectClientRuntimeRefOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetClientRuntimeRefResponse> execute(final ShardModel shardModel,
                                                    final GetClientRuntimeRefRequest request) {
        log.debug("{}", request);

        final var clientId = request.getClientId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectClientRuntimeRefOperation
                        .selectClientRuntimeRef(sqlConnection, shardModel.slot(), clientId, id))
                .map(GetClientRuntimeRefResponse::new);
    }
}
