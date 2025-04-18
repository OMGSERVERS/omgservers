package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantProjectIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantStagesMethodImpl implements ViewTenantStagesMethod {

    final SelectActiveTenantStagesByTenantProjectIdOperation selectActiveTenantStagesByTenantProjectIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantStagesResponse> execute(final ShardModel shardModel,
                                                 final ViewTenantStagesRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantStagesByTenantProjectIdOperation
                        .execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                tenantProjectId))
                .map(ViewTenantStagesResponse::new);

    }
}
