package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.viewClientMatchmakerRefs;

import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.service.module.client.impl.operation.clientMatchmakerRef.selectActiveClientMatchmakerRefsByClientId.SelectActiveClientMatchmakerRefsByClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewClientMatchmakerRefsMethodImpl implements ViewClientMatchmakerRefsMethod {

    final SelectActiveClientMatchmakerRefsByClientIdOperation selectActiveClientMatchmakerRefsByClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(
            final ViewClientMatchmakerRefsRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var clientId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveClientMatchmakerRefsByClientIdOperation
                            .selectActiveClientMatchmakerRefsByClientId(sqlConnection,
                                    shard.shard(),
                                    clientId
                            ));
                })
                .map(ViewClientMatchmakerRefsResponse::new);

    }
}
