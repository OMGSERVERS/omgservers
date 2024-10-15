package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectTenantDeploymentOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentMethodImpl implements GetTenantDeploymentMethod {

    final SelectTenantDeploymentOperation selectTenantDeploymentOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentResponse> execute(final GetTenantDeploymentRequest request) {
        log.debug("Get deployment, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentOperation
                            .execute(sqlConnection, shard, tenantId, tenantDeploymentId));
                })
                .map(GetTenantDeploymentResponse::new);
    }
}
