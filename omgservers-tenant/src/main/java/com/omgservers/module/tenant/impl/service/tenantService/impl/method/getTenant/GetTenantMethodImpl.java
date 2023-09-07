package com.omgservers.module.tenant.impl.service.tenantService.impl.method.getTenant;

import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.module.tenant.impl.operation.selectTenant.SelectTenantOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenant.GetTenantResponse;
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
        GetTenantRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                            .selectTenant(sqlConnection, shard.shard(), id));
                })
                .map(GetTenantResponse::new);
    }
}
