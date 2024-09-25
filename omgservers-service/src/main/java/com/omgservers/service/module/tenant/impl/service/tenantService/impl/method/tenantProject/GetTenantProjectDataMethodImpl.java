package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantProjectPermission.SelectActiveTenantProjectPermissionsByTenantProjectIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantProjectIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectActiveTenantVersionProjectionsByTenantProjectIdOperation;
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
class GetTenantProjectDataMethodImpl implements GetTenantProjectDataMethod {

    final SelectActiveTenantProjectPermissionsByTenantProjectIdOperation
            selectActiveTenantProjectPermissionsByTenantProjectIdOperation;
    final SelectActiveTenantVersionProjectionsByTenantProjectIdOperation
            selectActiveTenantVersionProjectionsByTenantProjectIdOperation;
    final SelectActiveTenantStagesByTenantProjectIdOperation
            selectActiveTenantStagesByTenantProjectIdOperation;
    final SelectTenantProjectOperation selectTenantProjectOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantProjectDataResponse> execute(final GetTenantProjectDataRequest request) {
        log.debug("Get tenant project data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantProjectid = request.getTenantProjectId();
                    final var tenantProjectData = new TenantProjectDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, tenantProjectid, tenantProjectData));
                })
                .map(GetTenantProjectDataResponse::new);
    }

    Uni<TenantProjectDataDto> fillData(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final Long tenantProjectId,
                                       final TenantProjectDataDto tenantProjectData) {
        return fillTenantProject(sqlConnection, shard, tenantId, tenantProjectId, tenantProjectData)
                .flatMap(voidItem -> fillTenantProjectPermissions(sqlConnection, shard, tenantId, tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillTenantStages(sqlConnection, shard, tenantId, tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillTenantVersionProjections(sqlConnection, shard, tenantId, tenantProjectId,
                        tenantProjectData))
                .replaceWith(tenantProjectData);
    }

    Uni<Void> fillTenantProject(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long tenantProjectId,
                                final TenantProjectDataDto tenantProjectData) {
        return selectTenantProjectOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setTenantProject)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantProjectPermissions(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long tenantProjectId,
                                           final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantProjectPermissionsByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setTenantProjectPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantStages(final SqlConnection sqlConnection,
                               final int shard,
                               final Long tenantId,
                               final Long tenantProjectId,
                               final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantStagesByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setTenantStages)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantVersionProjections(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long tenantProjectId,
                                           final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantVersionProjectionsByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setTenantVersionProjections)
                .replaceWithVoid();
    }
}
