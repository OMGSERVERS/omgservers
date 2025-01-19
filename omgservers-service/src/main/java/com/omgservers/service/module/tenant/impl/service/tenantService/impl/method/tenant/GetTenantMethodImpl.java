package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.service.module.tenant.impl.operation.tenant.SelectTenantOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                            .execute(sqlConnection, shard.shard(), id));
                })
                .map(GetTenantResponse::new);
    }
}
