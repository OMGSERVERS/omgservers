package com.omgservers.service.module.client.impl.service.clientService.impl.method.selectClientRuntime;

import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.client.impl.operation.selectActiveClientRuntimesByClientId.SelectActiveClientRuntimesByClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectClientRuntimeMethodImpl implements SelectClientRuntimeMethod {

    final SelectActiveClientRuntimesByClientIdOperation selectActiveClientRuntimesByClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SelectClientRuntimeResponse> selectClientRuntime(final SelectClientRuntimeRequest request) {
        log.debug("Select client runtime, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveClientRuntimesByClientIdOperation
                            .selectActiveClientRuntimesByClientId(sqlConnection,
                                    shard.shard(),
                                    clientId
                            )
                            .map(clientRuntimes -> {
                                if (clientRuntimes.isEmpty()) {
                                    throw new ServerSideConflictException(
                                            String.format("Client runtime was not select, " +
                                                    "there aren't active client runtimes, " +
                                                    "clientId=%s", clientId));
                                }

                                final var strategy = request.getStrategy();
                                return switch (strategy) {
                                    case LATEST -> clientRuntimes.get(clientRuntimes.size() - 1);
                                };
                            }));
                })
                .map(SelectClientRuntimeResponse::new);

    }
}
