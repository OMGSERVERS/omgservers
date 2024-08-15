package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant.getTenantData;

import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.module.tenant.impl.operation.project.selectActiveProjectsByTenantId.SelectActiveProjectsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.projectPermission.selectActiveProjectPermissionsByTenantId.SelectActiveProjectPermissionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByTenantId.SelectActiveStagesByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.stagePermission.selectActiveStagePermissionsByTenantId.SelectActiveStagePermissionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenant.selectTenant.SelectTenantOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.selectActiveTenantPermissionsByTenantId.SelectActiveTenantPermissionsByTenantIdOperation;
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
class GetTenantDataMethodImpl implements GetTenantDataMethod {

    final SelectActiveProjectPermissionsByTenantIdOperation selectActiveProjectPermissionsByTenantIdOperation;
    final SelectActiveTenantPermissionsByTenantIdOperation selectActiveTenantPermissionsByTenantIdOperation;
    final SelectActiveStagePermissionsByTenantIdOperation selectActiveStagePermissionsByTenantIdOperation;
    final SelectActiveProjectsByTenantIdOperation selectActiveProjectsByTenantIdOperation;
    final SelectActiveStagesByTenantIdOperation selectActiveStagesByTenantIdOperation;
    final SelectTenantOperation selectTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        log.debug("Get tenant data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantData = new TenantDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, tenantData));
                })
                .map(GetTenantDataResponse::new);
    }

    Uni<TenantDataDto> fillData(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final TenantDataDto tenantData) {
        return selectTenant(sqlConnection, shard, tenantId, tenantData)
                .flatMap(voidItem -> fillTenantPermissions(sqlConnection, shard, tenantId, tenantData))
                .flatMap(voidItem -> fillProjects(sqlConnection, shard, tenantId, tenantData))
                .flatMap(voidItem -> fillProjectPermissions(sqlConnection, shard, tenantId, tenantData))
                .flatMap(voidItem -> fillStages(sqlConnection, shard, tenantId, tenantData))
                .flatMap(voidItem -> fillStagePermissions(sqlConnection, shard, tenantId, tenantData))
                .replaceWith(tenantData);
    }

    Uni<Void> selectTenant(final SqlConnection sqlConnection,
                           final int shard,
                           final Long tenantId,
                           final TenantDataDto tenantDetails) {
        return selectTenantOperation.selectTenant(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDetails::setTenant)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantPermissions(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final TenantDataDto tenantData) {
        return selectActiveTenantPermissionsByTenantIdOperation.selectActiveTenantPermissionsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setTenantPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillProjects(final SqlConnection sqlConnection,
                           final int shard,
                           final Long tenantId,
                           final TenantDataDto tenantData) {
        return selectActiveProjectsByTenantIdOperation.selectActiveProjectsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setProjects)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectPermissions(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final TenantDataDto tenantData) {
        return selectActiveProjectPermissionsByTenantIdOperation.selectActiveProjectPermissionsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setProjectPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillStages(final SqlConnection sqlConnection,
                         final int shard,
                         final Long tenantId,
                         final TenantDataDto tenantData) {
        return selectActiveStagesByTenantIdOperation.selectActiveStagesByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setStages)
                .replaceWithVoid();
    }

    Uni<Void> fillStagePermissions(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final TenantDataDto tenantData) {
        return selectActiveStagePermissionsByTenantIdOperation.selectActiveStagePermissionsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setStagePermissions)
                .replaceWithVoid();
    }
}
