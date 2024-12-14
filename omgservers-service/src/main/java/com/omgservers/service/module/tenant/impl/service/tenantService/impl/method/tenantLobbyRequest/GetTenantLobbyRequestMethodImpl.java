package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.GetTenantLobbyRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.SelectTenantLobbyRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantLobbyRequestMethodImpl implements GetTenantLobbyRequestMethod {

    final SelectTenantLobbyRequestOperation selectTenantLobbyRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantLobbyRequestResponse> execute(final GetTenantLobbyRequestRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyRequestOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantLobbyRequestResponse::new);
    }
}
