package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.SelectActiveTenantDeploymentRefsByStageIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantDeploymentRefsMethodImpl implements ViewTenantDeploymentRefsMethod {

    final SelectActiveTenantDeploymentRefsByStageIdOperation selectActiveTenantDeploymentRefsByStageIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantDeploymentRefsResponse> execute(final ShardModel shardModel,
                                                         final ViewTenantDeploymentRefsRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(
                        sqlConnection -> selectActiveTenantDeploymentRefsByStageIdOperation
                                .execute(sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        tenantStageId)
                )
                .map(ViewTenantDeploymentRefsResponse::new);

    }
}
