package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectTenantVersionConfigOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantVersionConfigMethodImpl implements GetTenantVersionConfigMethod {

    final SelectTenantVersionConfigOperation selectTenantVersionConfigOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(GetTenantVersionConfigRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantVersionId = request.getTenantVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantVersionConfigOperation
                            .execute(sqlConnection, shard, tenantId, tenantVersionId));
                })
                .map(GetTenantVersionConfigResponse::new);
    }
}
