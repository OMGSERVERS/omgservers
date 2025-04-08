package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.SelectActiveTenantProjectsByTenantIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantProjectsMethodImpl implements ViewTenantProjectsMethod {

    final SelectActiveTenantProjectsByTenantIdOperation selectActiveTenantProjectsByTenantIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantProjectsResponse> execute(final ShardModel shardModel,
                                                   final ViewTenantProjectsRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantProjectsByTenantIdOperation
                        .execute(sqlConnection,
                                shardModel.shard(),
                                tenantId
                        ))
                .map(ViewTenantProjectsResponse::new);

    }
}
