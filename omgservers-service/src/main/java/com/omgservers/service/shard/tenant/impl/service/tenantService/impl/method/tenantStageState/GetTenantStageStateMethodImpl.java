package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStageState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.model.tenantStageState.TenantStageStateDto;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateRequest;
import com.omgservers.schema.shard.tenant.tenantStageState.GetTenantStageStateResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectActiveTenantDeploymentResourcesByStageIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.SelectActiveTenantStageCommandsByTenantStageIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantStageStateMethodImpl implements GetTenantStageStateMethod {

    final SelectActiveTenantStageCommandsByTenantStageIdOperation
            selectActiveTenantStageCommandsByTenantStageIdOperation;
    final SelectActiveTenantDeploymentResourcesByStageIdOperation
            selectActiveTenantDeploymentResourcesByStageIdOperation;
    final SelectTenantStageOperation selectTenantStageOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageStateResponse> execute(final ShardModel shardModel,
                                                    final GetTenantStageStateRequest request) {
        log.debug("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var tenantStageState = new TenantStageStateDto();
                    return selectTenantStageOperation.execute(sqlConnection, slot, tenantId, tenantStageId)
                            .invoke(tenantStageState::setTenantStage)
                            .flatMap(tenantStage ->
                                    selectActiveTenantStageCommandsByTenantStageIdOperation
                                            .execute(sqlConnection, slot, tenantId, tenantStageId))
                            .invoke(tenantStageState::setTenantStageCommands)
                            .flatMap(tenantStageCommands ->
                                    selectActiveTenantDeploymentResourcesByStageIdOperation
                                            .execute(sqlConnection, slot, tenantId, tenantStageId))
                            .invoke(tenantStageState::setTenantDeploymentResources)
                            .replaceWith(tenantStageState);
                })
                .map(GetTenantStageStateResponse::new);
    }
}
