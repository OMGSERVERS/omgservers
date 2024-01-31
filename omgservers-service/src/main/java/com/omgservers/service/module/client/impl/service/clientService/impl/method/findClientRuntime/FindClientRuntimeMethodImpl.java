package com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientRuntime;

import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import com.omgservers.service.module.client.impl.operation.selectClientRuntimeByClientIdAndRuntimeId.SelectClientRuntimeByClientIdAndRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindClientRuntimeMethodImpl implements FindClientRuntimeMethod {

    final SelectClientRuntimeByClientIdAndRuntimeIdOperation selectClientRuntimeByClientIdAndRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindClientRuntimeResponse> findClientRuntime(final FindClientRuntimeRequest request) {
        log.debug("Find client runtime, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectClientRuntimeByClientIdAndRuntimeIdOperation
                            .selectClientRuntimeByClientIdAndRuntimeId(sqlConnection,
                                    shard.shard(),
                                    clientId,
                                    runtimeId));
                })
                .map(FindClientRuntimeResponse::new);
    }
}
