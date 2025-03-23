package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.ViewTenantMatchmakerResourcesResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.SelectActiveTenantMatchmakerResourcesByTenantDeploymentIdOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantMatchmakerResourcesMethodImpl implements ViewTenantMatchmakerResourcesMethod {

    final SelectActiveTenantMatchmakerResourcesByTenantDeploymentIdOperation
            selectActiveTenantMatchmakerResourcesByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantMatchmakerResourcesResponse> execute(
            final ViewTenantMatchmakerResourcesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantMatchmakerResourcesByTenantDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantDeploymentId));
                })
                .map(ViewTenantMatchmakerResourcesResponse::new);

    }
}
