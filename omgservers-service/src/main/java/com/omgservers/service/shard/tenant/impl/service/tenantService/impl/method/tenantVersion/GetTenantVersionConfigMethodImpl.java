package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.SelectTenantVersionConfigOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantVersionConfigMethodImpl implements GetTenantVersionConfigMethod {

    final SelectTenantVersionConfigOperation selectTenantVersionConfigOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionConfigResponse> execute(final ShardModel shardModel,
                                                       final GetTenantVersionConfigRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getTenantVersionId();
        return pgPool.withTransaction(sqlConnection -> selectTenantVersionConfigOperation
                        .execute(sqlConnection, shardModel.shard(), tenantId, tenantVersionId))
                .map(GetTenantVersionConfigResponse::new);
    }
}
