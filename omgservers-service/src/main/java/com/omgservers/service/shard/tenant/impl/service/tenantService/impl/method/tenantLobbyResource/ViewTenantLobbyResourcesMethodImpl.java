package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.SelectActiveTenantLobbyResourcesByTenantDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantLobbyResourcesMethodImpl implements ViewTenantLobbyResourcesMethod {

    final SelectActiveTenantLobbyResourcesByTenantDeploymentIdOperation
            selectActiveTenantLobbyResourcesByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantLobbyResourcesResponse> execute(final ViewTenantLobbyResourcesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(sqlConnection ->
                            selectActiveTenantLobbyResourcesByTenantDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantDeploymentId
                                    )
                    );
                })
                .map(ViewTenantLobbyResourcesResponse::new);

    }
}
