package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.GetTenantLobbyResourceResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.SelectTenantLobbyResourceOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantLobbyResourceMethodImpl implements GetTenantLobbyResourceMethod {

    final SelectTenantLobbyResourceOperation selectTenantLobbyResourceOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantLobbyResourceResponse> execute(final GetTenantLobbyResourceRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyResourceOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantLobbyResourceResponse::new);
    }
}
