package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionLobbyRef;

import com.omgservers.model.dto.tenant.GetVersionLobbyRefRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRef.SelectVersionLobbyRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
