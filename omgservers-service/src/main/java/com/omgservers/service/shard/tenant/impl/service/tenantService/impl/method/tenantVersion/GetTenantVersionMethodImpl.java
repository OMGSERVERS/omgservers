package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantVersionMethodImpl implements GetTenantVersionMethod {

    final SelectTenantVersionOperation selectTenantVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantVersionResponse> execute(final ShardModel shardModel,
                                                 final GetTenantVersionRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantVersionOperation
                        .execute(sqlConnection, slot, tenantId, id))
                .map(GetTenantVersionResponse::new);
    }
}
