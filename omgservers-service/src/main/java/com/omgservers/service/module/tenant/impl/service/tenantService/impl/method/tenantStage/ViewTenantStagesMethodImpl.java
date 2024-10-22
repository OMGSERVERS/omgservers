package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.module.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantProjectIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantStagesResponse> execute(final ViewTenantStagesRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var tenantProjectId = request.getTenantProjectId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveTenantStagesByTenantProjectIdOperation
                            .execute(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    tenantProjectId
                            )
                    );
                })
                .map(ViewTenantStagesResponse::new);

    }
}
