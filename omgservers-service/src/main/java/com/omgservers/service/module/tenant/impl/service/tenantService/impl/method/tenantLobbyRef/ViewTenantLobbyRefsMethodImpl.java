package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantLobbyRefsMethodImpl implements ViewTenantLobbyRefsMethod {

    final SelectActiveTenantLobbyRefsByTenantDeploymentIdOperation
            selectActiveTenantLobbyRefsByTenantDeploymentIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantLobbyRefsResponse> execute(final ViewTenantLobbyRefsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    return pgPool.withTransaction(
                            sqlConnection -> selectActiveTenantLobbyRefsByTenantDeploymentIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantDeploymentId
                                    )
                    );
                })
                .map(ViewTenantLobbyRefsResponse::new);

    }
}
