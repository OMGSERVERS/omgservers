package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectActiveTenantDeploymentResourcesByStageIdAndStatusOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectActiveTenantDeploymentResourcesByStageIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantDeploymentResourcesMethodImpl implements ViewTenantDeploymentResourcesMethod {

    final SelectActiveTenantDeploymentResourcesByStageIdAndStatusOperation
            selectActiveTenantDeploymentResourcesByStageIdAndStatusOperation;
    final SelectActiveTenantDeploymentResourcesByStageIdOperation
            selectActiveTenantDeploymentResourcesByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(final ViewTenantDeploymentResourcesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    return pgPool.withTransaction(
                            sqlConnection -> {
                                final var status = request.getStatus();
                                if (Objects.nonNull(status)) {
                                    return selectActiveTenantDeploymentResourcesByStageIdAndStatusOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    tenantStageId,
                                                    status);
                                } else {
                                    return selectActiveTenantDeploymentResourcesByStageIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    tenantStageId
                                            );
                                }
                            }
                    );
                })
                .map(ViewTenantDeploymentResourcesResponse::new);

    }
}
