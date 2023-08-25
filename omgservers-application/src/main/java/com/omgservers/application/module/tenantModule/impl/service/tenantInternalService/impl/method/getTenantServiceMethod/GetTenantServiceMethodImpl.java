package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.impl.method.getTenantServiceMethod;

import com.omgservers.application.module.tenantModule.impl.operation.selectTenantOperation.SelectTenantOperation;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantServiceMethodImpl implements GetTenantServiceMethod {

    final SelectTenantOperation selectTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantResponse> getTenant(final GetTenantInternalRequest request) {
        GetTenantInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                            .selectTenant(sqlConnection, shard.shard(), id));
                })
                .map(GetTenantResponse::new);
    }
}
