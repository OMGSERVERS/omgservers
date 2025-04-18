package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectTenantDeploymentResourceByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantDeploymentResourceMethodImpl implements FindTenantDeploymentResourceMethod {

    final SelectTenantDeploymentResourceByDeploymentIdOperation selectTenantDeploymentResourceByDeploymentIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantDeploymentResourceResponse> execute(final ShardModel shardModel,
                                                             final FindTenantDeploymentResourceRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentResourceByDeploymentIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                deploymentId))
                .map(FindTenantDeploymentResourceResponse::new);
    }
}
