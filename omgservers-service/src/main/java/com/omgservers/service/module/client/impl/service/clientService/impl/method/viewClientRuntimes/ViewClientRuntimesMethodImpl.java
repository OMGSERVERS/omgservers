package com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientRuntimes;

import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
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
class ViewClientRuntimesMethodImpl implements ViewClientRuntimesMethod {

    final SelectActiveClientRuntimesByClientIdOperation selectActiveClientRuntimesByClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewClientRuntimesResponse> viewClientRuntimes(final ViewClientRuntimesRequest request) {
        log.debug("View client runtimes, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveClientRuntimesByClientIdOperation
                            .selectActiveClientRuntimesByClientId(sqlConnection,
                                    shard.shard(),
                                    clientId
                            ));
                })
                .map(ViewClientRuntimesResponse::new);

    }
}
