package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.FindTenantDeploymentRefResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.SelectTenantDeploymentRefByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantDeploymentRefMethodImpl implements FindTenantDeploymentRefMethod {

    final SelectTenantDeploymentRefByDeploymentIdOperation selectTenantDeploymentRefByDeploymentIdOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantDeploymentRefResponse> execute(ShardModel shardModel,
                                                        final FindTenantDeploymentRefRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var deploymentId = request.getDeploymentId();
        return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentRefByDeploymentIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                deploymentId))
                .map(FindTenantDeploymentRefResponse::new);
    }
}
