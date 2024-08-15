package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenant;

import com.omgservers.schema.module.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.GetTenantResponse;
import com.omgservers.service.module.tenant.impl.operation.tenant.selectTenant.SelectTenantOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantMethodImpl implements GetTenantMethod {

    final SelectTenantOperation selectTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        log.debug("Get tenant, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                            .selectTenant(sqlConnection, shard.shard(), id));
                })
                .map(GetTenantResponse::new);
    }
}
