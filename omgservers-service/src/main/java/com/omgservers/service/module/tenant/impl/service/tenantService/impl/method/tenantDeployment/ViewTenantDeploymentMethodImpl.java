package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantDeploymentMethodImpl implements ViewTenantDeploymentMethod {

    final SelectActiveTenantDeploymentsByTenantStageIdOperation selectActiveTenantDeploymentsByTenantStageIdOperation;
    final SelectActiveTenantDeploymentsByTenantIdOperation selectActiveTenantDeploymentsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantDeploymentsResponse> execute(final ViewTenantDeploymentsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                                final var tenantStageId = request.getTenantStageId();
                                if (Objects.nonNull(tenantStageId)) {
                                    return selectActiveTenantDeploymentsByTenantStageIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    tenantStageId);
                                } else {
                                    return selectActiveTenantDeploymentsByTenantIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId);
                                }
                            }
                    );
                })
                .map(ViewTenantDeploymentsResponse::new);
    }
}
