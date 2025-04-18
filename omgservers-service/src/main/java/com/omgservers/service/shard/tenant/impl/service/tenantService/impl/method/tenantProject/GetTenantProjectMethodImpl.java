package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class
GetTenantProjectMethodImpl implements GetTenantProjectMethod {

    final SelectTenantProjectOperation selectTenantProjectOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetTenantProjectResponse> execute(final ShardModel shardModel,
                                                 final GetTenantProjectRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantProjectOperation
                        .execute(sqlConnection, shardModel.slot(), tenantId, id))
                .map(GetTenantProjectResponse::new);
    }
}
