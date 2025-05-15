package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantDeploymentResourcesResponse> execute(final ShardModel shardModel,
                                                              final ViewTenantDeploymentResourcesRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var status = request.getStatus();
                    if (Objects.nonNull(status)) {
                        return selectActiveTenantDeploymentResourcesByStageIdAndStatusOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantStageId,
                                        status);
                    } else {
                        return selectActiveTenantDeploymentResourcesByStageIdOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantStageId
                                );
                    }
                })
                .map(ViewTenantDeploymentResourcesResponse::new);

    }
}
