package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantStageMethodImpl implements GetTenantStageMethod {

    final SelectTenantStageOperation selectTenantStageOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageResponse> execute(final ShardModel shardModel,
                                               final GetTenantStageRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantStageOperation
                        .execute(sqlConnection, shardModel.slot(), tenantId, id))
                .map(GetTenantStageResponse::new);
    }
}
