package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.GetTenantLobbyRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectTenantLobbyRefOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantLobbyRefMethodImpl implements GetTenantLobbyRefMethod {

    final SelectTenantLobbyRefOperation selectTenantLobbyRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantLobbyRefResponse> execute(final GetTenantLobbyRefRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyRefOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantLobbyRefResponse::new);
    }
}
