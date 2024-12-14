package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantStageIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.SelectActiveTenantStagePermissionsByTenantStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantStageDataMethodImpl implements GetTenantStageDataMethod {

    final SelectActiveTenantStagePermissionsByTenantStageIdOperation
            selectActiveTenantStagePermissionsByTenantStageIdOperation;
    final SelectActiveTenantDeploymentsByTenantStageIdOperation
            selectActiveTenantDeploymentsByTenantStageIdOperation;
    final SelectTenantStageOperation selectTenantStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageDataResponse> execute(final GetTenantStageDataRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    final var tenantStageData = new TenantStageDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, tenantStageId, tenantStageData));
                })
                .map(GetTenantStageDataResponse::new);
    }

    Uni<TenantStageDataDto> fillData(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final Long tenantStageId,
                                     final TenantStageDataDto stageData) {
        return fillTenantState(sqlConnection, shard, tenantId, tenantStageId, stageData)
                .flatMap(voidItem -> fillTenantStagePermissions(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId,
                        stageData))
                .flatMap(voidItem -> fillTenantDeployments(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId,
                        stageData))
                .replaceWith(stageData);
    }

    Uni<Void> fillTenantState(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long tenantStageId,
                              final TenantStageDataDto stageData) {
        return selectTenantStageOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setTenantStage)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantStagePermissions(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long tenantId,
                                         final Long tenantStageId,
                                         final TenantStageDataDto stageData) {
        return selectActiveTenantStagePermissionsByTenantStageIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setTenantStagePermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantDeployments(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final Long tenantStageId,
                                    final TenantStageDataDto stageData) {
        return selectActiveTenantDeploymentsByTenantStageIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setTenantDeployments)
                .replaceWithVoid();
    }
}
