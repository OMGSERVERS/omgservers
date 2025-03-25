package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.SelectClientRuntimeRefByClientIdAndRuntimeIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    public Uni<FindClientRuntimeRefResponse> execute(final FindClientRuntimeRefRequest request) {
        log.trace("{}", request);

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
