package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.findClientRuntimeRef;

import com.omgservers.schema.module.client.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.FindClientRuntimeRefResponse;
import com.omgservers.service.module.client.impl.operation.clientRuntimeRef.selectClientRuntimeRefByClientIdAndRuntimeId.SelectClientRuntimeRefByClientIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(final FindClientRuntimeRefRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectClientRuntimeRefByClientIdAndRuntimeIdOperation
                            .selectClientRuntimeRefByClientIdAndRuntimeId(sqlConnection,
                                    shard.shard(),
                                    clientId,
                                    runtimeId));
                })
                .map(FindClientRuntimeRefResponse::new);
    }
}
