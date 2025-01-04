package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectActiveTenantVersionProjectionsByTenantProjectIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantVersionsMethodImpl implements ViewTenantVersionsMethod {

    final SelectActiveTenantVersionProjectionsByTenantProjectIdOperation
            selectActiveTenantVersionProjectionsByTenantProjectIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantVersionsResponse> execute(final ViewTenantVersionsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantProjectId = request.getTenantProjectId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveTenantVersionProjectionsByTenantProjectIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    tenantProjectId
                            )
                    );
                })
                .map(ViewTenantVersionsResponse::new);
    }
}
