package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectTenantDeploymentResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantDeploymentResourceMethodImpl implements GetTenantDeploymentResourceMethod {

    final SelectTenantDeploymentResourceOperation selectTenantDeploymentResourceOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDeploymentResourceResponse> execute(final GetTenantDeploymentResourceRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentResourceOperation
                            .execute(sqlConnection, shard, tenantId, id));
                })
                .map(GetTenantDeploymentResourceResponse::new);
    }
}
