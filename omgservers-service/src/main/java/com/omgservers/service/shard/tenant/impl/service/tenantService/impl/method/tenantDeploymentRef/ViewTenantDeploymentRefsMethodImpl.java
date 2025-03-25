package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.module.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.ViewTenantDeploymentRefsResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantDeploymentRefsResponse> execute(final ViewTenantDeploymentRefsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantDeploymentRefsByStageIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantStageId)
                    );
                })
                .map(ViewTenantDeploymentRefsResponse::new);

    }
}
