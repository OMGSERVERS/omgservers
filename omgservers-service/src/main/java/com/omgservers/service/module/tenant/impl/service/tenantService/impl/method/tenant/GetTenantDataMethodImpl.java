package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.SelectActiveTenantProjectsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.SelectActiveTenantProjectPermissionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.SelectActiveTenantStagePermissionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenant.SelectTenantOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.SelectActiveTenantPermissionsByTenantIdOperation;
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

    final SelectActiveTenantProjectPermissionsByTenantIdOperation
            selectActiveTenantProjectPermissionsByTenantIdOperation;
    final SelectActiveTenantPermissionsByTenantIdOperation selectActiveTenantPermissionsByTenantIdOperation;
    final SelectActiveTenantStagePermissionsByTenantIdOperation selectActiveTenantStagePermissionsByTenantIdOperation;
    final SelectActiveTenantProjectsByTenantIdOperation selectActiveTenantProjectsByTenantIdOperation;
    final SelectActiveTenantStagesByTenantIdOperation selectActiveTenantStagesByTenantIdOperation;
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
        return selectTenantOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDetails::setTenant)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantPermissions(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final TenantDataDto tenantData) {
        return selectActiveTenantPermissionsByTenantIdOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setTenantPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillProjects(final SqlConnection sqlConnection,
                           final int shard,
                           final Long tenantId,
                           final TenantDataDto tenantData) {
        return selectActiveTenantProjectsByTenantIdOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setProjects)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectPermissions(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final TenantDataDto tenantData) {
        return selectActiveTenantProjectPermissionsByTenantIdOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setProjectPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillStages(final SqlConnection sqlConnection,
                         final int shard,
                         final Long tenantId,
                         final TenantDataDto tenantData) {
        return selectActiveTenantStagesByTenantIdOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setStages)
                .replaceWithVoid();
    }

    Uni<Void> fillStagePermissions(final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long tenantId,
                                   final TenantDataDto tenantData) {
        return selectActiveTenantStagePermissionsByTenantIdOperation.execute(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantData::setStagePermissions)
                .replaceWithVoid();
    }
}