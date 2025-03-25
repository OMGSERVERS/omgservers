package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.SelectTenantDeploymentRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentRefMethodImpl implements GetTenantDeploymentRefMethod {

    final SelectTenantDeploymentRefOperation selectTenantDeploymentRefOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentRefResponse> execute(final GetTenantDeploymentRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentRefOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantDeploymentRefResponse::new);
    }
}
