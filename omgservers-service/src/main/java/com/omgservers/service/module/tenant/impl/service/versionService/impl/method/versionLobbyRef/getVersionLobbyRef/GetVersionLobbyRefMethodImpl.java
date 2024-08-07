package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.getVersionLobbyRef;

import com.omgservers.schema.module.tenant.GetVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.GetVersionLobbyRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRef.SelectVersionLobbyRefOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionLobbyRefMethodImpl implements GetVersionLobbyRefMethod {

    final SelectVersionLobbyRefOperation selectVersionLobbyRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionLobbyRefResponse> getVersionLobbyRef(final GetVersionLobbyRefRequest request) {
        log.debug("Get version lobby ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionLobbyRefOperation
                            .selectVersionLobbyRef(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionLobbyRefResponse::new);
    }
}
