package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetTenantProjectResponse> execute(final GetTenantProjectRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantProjectOperation
                            .execute(sqlConnection, shard.shard(), tenantId, id));
                })
                .map(GetTenantProjectResponse::new);
    }
}
