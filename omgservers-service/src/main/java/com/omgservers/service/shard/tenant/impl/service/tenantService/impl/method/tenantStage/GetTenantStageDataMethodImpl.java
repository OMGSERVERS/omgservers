package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.shard.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.operation.alias.ViewPtrAliasesOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.SelectActiveTenantDeploymentResourcesByStageIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.SelectActiveTenantStagePermissionsByTenantStageIdOperation;
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

    final AliasShard aliasShard;

    final SelectActiveTenantStagePermissionsByTenantStageIdOperation
            selectActiveTenantStagePermissionsByTenantStageIdOperation;
    final SelectActiveTenantDeploymentResourcesByStageIdOperation
            selectActiveTenantDeploymentResourcesByStageIdOperation;
    final SelectTenantStageOperation selectTenantStageOperation;

    final ViewPtrAliasesOperation viewPtrAliasesOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageDataResponse> execute(final ShardModel shardModel,
                                                   final GetTenantStageDataRequest request) {
        log.trace("{}", request);

        final int slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getTenantStageId();
        final var tenantStageData = new TenantStageDataDto();

        return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId,
                        tenantStageData))
                .flatMap(voidItem -> fillAliases(tenantStageId,
                        tenantStageData))
                .replaceWith(tenantStageData)
                .map(GetTenantStageDataResponse::new);
    }

    Uni<TenantStageDataDto> fillData(final SqlConnection sqlConnection,
                                     final int slot,
                                     final Long tenantId,
                                     final Long tenantStageId,
                                     final TenantStageDataDto tenantStageData) {
        return fillStage(sqlConnection, slot, tenantId, tenantStageId, tenantStageData)
                .flatMap(voidItem -> fillPermissions(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId,
                        tenantStageData))
                .flatMap(voidItem -> fillDeployments(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId,
                        tenantStageData))
                .replaceWith(tenantStageData);
    }

    Uni<Void> fillStage(final SqlConnection sqlConnection,
                        final int slot,
                        final Long tenantId,
                        final Long tenantStageId,
                        final TenantStageDataDto tenantStageData) {
        return selectTenantStageOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId)
                .invoke(tenantStageData::setStage)
                .replaceWithVoid();
    }

    Uni<Void> fillPermissions(final SqlConnection sqlConnection,
                              final int slot,
                              final Long tenantId,
                              final Long tenantStageId,
                              final TenantStageDataDto stageData) {
        return selectActiveTenantStagePermissionsByTenantStageIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillDeployments(final SqlConnection sqlConnection,
                              final int slot,
                              final Long tenantId,
                              final Long tenantStageId,
                              final TenantStageDataDto stageData) {
        return selectActiveTenantDeploymentResourcesByStageIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setDeployments)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantStageId,
                          final TenantStageDataDto tenantStageData) {
        return viewPtrAliasesOperation.execute(tenantStageId)
                .invoke(tenantStageData::setAliases)
                .replaceWithVoid();
    }
}
