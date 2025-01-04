package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest.SelectActiveTenantMatchmakerRequestsByTenantDeploymentIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantMatchmakerRequestsMethodImpl implements ViewTenantMatchmakerRequestsMethod {

    final SelectActiveTenantMatchmakerRequestsByTenantDeploymentIdOperation
            selectActiveTenantMatchmakerRequestsByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantMatchmakerRequestsResponse> execute(
            final ViewTenantMatchmakerRequestsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantMatchmakerRequestsByTenantDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantDeploymentId));
                })
                .map(ViewTenantMatchmakerRequestsResponse::new);

    }
}
