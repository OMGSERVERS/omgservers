package com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientRuntime;

import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import com.omgservers.service.module.client.impl.operation.selectClientRuntime.SelectClientRuntimeOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetClientRuntimeMethodImpl implements GetClientRuntimeMethod {

    final SelectClientRuntimeOperation selectClientRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetClientRuntimeResponse> getClientRuntime(final GetClientRuntimeRequest request) {
        log.debug("Get client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectClientRuntimeOperation
                            .selectClientRuntime(sqlConnection, shard.shard(), clientId, id));
                })
                .map(GetClientRuntimeResponse::new);
    }
}
