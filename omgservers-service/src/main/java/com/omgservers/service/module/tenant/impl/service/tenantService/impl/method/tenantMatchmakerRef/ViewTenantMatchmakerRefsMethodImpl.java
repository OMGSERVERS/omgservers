package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantMatchmakerRefsMethodImpl implements ViewTenantMatchmakerRefsMethod {

    final SelectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation
            selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantMatchmakerRefsResponse> execute(
            final ViewTenantMatchmakerRefsRequest request) {
        log.debug("View tenant matchmaker refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantMatchmakerRefsByTenantDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantDeploymentId));
                })
                .map(ViewTenantMatchmakerRefsResponse::new);

    }
}
