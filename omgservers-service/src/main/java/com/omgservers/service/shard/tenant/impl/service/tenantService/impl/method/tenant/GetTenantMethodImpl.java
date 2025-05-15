package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenant.SelectTenantOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<GetTenantResponse> getTenant(final ShardModel shardModel,
                                            final GetTenantRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                        .execute(sqlConnection, shardModel.slot(), id))
                .map(GetTenantResponse::new);
    }
}
