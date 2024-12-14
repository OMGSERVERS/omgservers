package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantVersionMethodImpl implements GetTenantVersionMethod {

    final SelectTenantVersionOperation selectTenantVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionResponse> execute(GetTenantVersionRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantVersionOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantVersionResponse::new);
    }
}
