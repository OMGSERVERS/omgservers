package com.omgservers.service.module.server.impl.service.serverService.impl.method.serverContainer.viewServerContainers;

import com.omgservers.model.dto.server.ViewServerContainersRequest;
import com.omgservers.model.dto.server.ViewServerContainersResponse;
import com.omgservers.service.module.server.impl.operation.serverContainer.selectActiveServerContainersByServerId.SelectActiveServerContainersByServerIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewServerContainersMethodImpl implements ViewServerContainersMethod {

    final SelectActiveServerContainersByServerIdOperation selectActiveServerContainersByServerIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewServerContainersResponse> viewServerContainers(
            final ViewServerContainersRequest request) {
        log.debug("View client matchmaker refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var serverId = request.getServerId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveServerContainersByServerIdOperation
                            .selectActiveServerContainersByServerId(sqlConnection,
                                    shard.shard(),
                                    serverId
                            ));
                })
                .map(ViewServerContainersResponse::new);

    }
}
