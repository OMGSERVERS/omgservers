package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.FindTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.FindTenantDeploymentRefResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantDeploymentRefResponse> execute(final FindTenantDeploymentRefRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var deploymentId = request.getDeploymentId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentRefByDeploymentIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    deploymentId));
                })
                .map(FindTenantDeploymentRefResponse::new);
    }
}
