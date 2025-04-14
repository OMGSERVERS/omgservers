package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.SelectActiveTenantVersionProjectionsByTenantProjectIdOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantVersionsResponse> execute(final ShardModel shardModel,
                                                   final ViewTenantVersionsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        return pgPool.withTransaction(sqlConnection ->
                        selectActiveTenantVersionProjectionsByTenantProjectIdOperation.execute(sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                tenantProjectId))
                .map(ViewTenantVersionsResponse::new);
    }
}
