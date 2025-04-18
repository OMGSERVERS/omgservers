package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.shard.client.clientRuntimeRef.ViewClientRuntimeRefsResponse;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.SelectActiveClientRuntimeRefsByClientIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewClientRuntimeRefsResponse> execute(final ShardModel shardModel,
                                                      final ViewClientRuntimeRefsRequest request) {
        log.trace("{}", request);

        final var clientId = request.getClientId();
        return pgPool.withTransaction(sqlConnection -> selectActiveClientRuntimeRefsByClientIdOperation
                        .selectActiveClientRuntimeRefsByClientId(sqlConnection,
                                shardModel.slot(),
                                clientId
                        ))
                .map(ViewClientRuntimeRefsResponse::new);

    }
}
