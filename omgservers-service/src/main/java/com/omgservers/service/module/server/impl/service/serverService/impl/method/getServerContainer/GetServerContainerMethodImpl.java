package com.omgservers.service.module.server.impl.service.serverService.impl.method.getServerContainer;

import com.omgservers.model.dto.server.GetServerContainerRequest;
import com.omgservers.model.dto.server.GetServerContainerResponse;
import com.omgservers.service.module.server.impl.operation.selectServerContainer.SelectServerContainerOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServerContainerMethodImpl implements GetServerContainerMethod {

    final SelectServerContainerOperation selectServerContainerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetServerContainerResponse> getServerContainer(final GetServerContainerRequest request) {
        log.debug("Get client runtime ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var serverId = request.getServerId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectServerContainerOperation
                            .selectServerContainer(sqlConnection, shard.shard(), serverId, id));
                })
                .map(GetServerContainerResponse::new);
    }
}
