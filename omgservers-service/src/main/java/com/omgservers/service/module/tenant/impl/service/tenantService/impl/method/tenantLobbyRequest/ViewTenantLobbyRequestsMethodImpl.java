package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.SelectActiveTenantLobbyRequestsByTenantDeploymentIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantLobbyRequestsMethodImpl implements ViewTenantLobbyRequestsMethod {

    final SelectActiveTenantLobbyRequestsByTenantDeploymentIdOperation
            selectActiveTenantLobbyRequestsByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantLobbyRequestsResponse> execute(
            final ViewTenantLobbyRequestsRequest request) {
        log.debug("View tenant lobby requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getDeploymentId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveTenantLobbyRequestsByTenantDeploymentIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId
                            )
                    );
                })
                .map(ViewTenantLobbyRequestsResponse::new);

    }
}
