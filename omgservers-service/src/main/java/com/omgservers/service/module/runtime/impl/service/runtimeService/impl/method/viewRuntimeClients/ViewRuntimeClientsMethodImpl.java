package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeClients;

import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.service.module.runtime.impl.operation.selectActiveRuntimeClientsByRuntimeId.SelectActiveRuntimeClientsByRuntimeIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeClientsMethodImpl implements ViewRuntimeClientsMethod {

    final SelectActiveRuntimeClientsByRuntimeIdOperation selectActiveRuntimeClientsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeClientsResponse> viewRuntimeClients(final ViewRuntimeClientsRequest request) {
        log.debug("View runtime clients, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveRuntimeClientsByRuntimeIdOperation
                            .selectActiveRuntimeClientsByRuntimeId(sqlConnection,
                                    shard.shard(),
                                    runtimeId)
                    );
                })
                .map(ViewRuntimeClientsResponse::new);
    }
}
