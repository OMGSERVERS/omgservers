package com.omgservers.service.module.server.impl.service.serverService.impl.method.getServer;

import com.omgservers.model.dto.server.GetServerRequest;
import com.omgservers.model.dto.server.GetServerResponse;
import com.omgservers.service.module.server.impl.operation.selectServer.SelectServerOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServerMethodImpl implements GetServerMethod {

    final SelectServerOperation selectServerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetServerResponse> getServer(final GetServerRequest request) {
        log.debug("Get client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectServerOperation
                            .selectServer(sqlConnection, shard.shard(), id));
                })
                .map(GetServerResponse::new);
    }
}
