package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.viewClientRuntimeRefs;

import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.service.module.client.impl.operation.clientRuntimeRef.selectActiveClientRuntimeRefsByClientId.SelectActiveClientRuntimeRefsByClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewClientRuntimeRefsMethodImpl implements ViewClientRuntimeRefsMethod {

    final SelectActiveClientRuntimeRefsByClientIdOperation selectActiveClientRuntimeRefsByClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(final ViewClientRuntimeRefsRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveClientRuntimeRefsByClientIdOperation
                            .selectActiveClientRuntimeRefsByClientId(sqlConnection,
                                    shard.shard(),
                                    clientId
                            ));
                })
                .map(ViewClientRuntimeRefsResponse::new);

    }
}
