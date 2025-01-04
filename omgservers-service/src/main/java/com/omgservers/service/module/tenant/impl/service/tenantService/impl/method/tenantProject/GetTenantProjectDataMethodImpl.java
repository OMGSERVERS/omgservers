package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.module.alias.AliasModule;
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

    final AliasModule aliasModule;

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
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantProjectId = request.getTenantProjectId();
                    final var tenantProjectData = new TenantProjectDataDto();

                    return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                                    shard,
                                    tenantId,
                                    tenantProjectId,
                                    tenantProjectData))
                            .flatMap(voidItem -> fillAliases(tenantId,
                                    tenantProjectId,
                                    tenantProjectData))
                            .flatMap(voidItem -> fillProjectAliases(tenantId,
                                    tenantProjectId,
                                    tenantProjectData))
                            .replaceWith(tenantProjectData);
                })
                .map(GetTenantProjectDataResponse::new);
    }

    Uni<Void> fillData(final SqlConnection sqlConnection,
                       final int shard,
                       final Long tenantId,
                       final Long tenantProjectId,
                       final TenantProjectDataDto tenantProjectData) {
        return fillProject(sqlConnection, shard, tenantId, tenantProjectId, tenantProjectData)
                .flatMap(voidItem -> fillAliases(tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectPermissions(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectStages(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectVersions(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectAliases(tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .replaceWithVoid();
    }

    Uni<Void> fillProject(final SqlConnection sqlConnection,
                          final int shard,
                          final Long tenantId,
                          final Long tenantProjectId,
                          final TenantProjectDataDto tenantProjectData) {
        return selectTenantProjectOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProject)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectPermissions(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final Long tenantProjectId,
                                     final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantProjectPermissionsByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectStages(final SqlConnection sqlConnection,
                                final int shard,
                                final Long tenantId,
                                final Long tenantProjectId,
                                final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantStagesByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectStages)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectVersions(final SqlConnection sqlConnection,
                                  final int shard,
                                  final Long tenantId,
                                  final Long tenantProjectId,
                                  final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantVersionProjectionsByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectVersions)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantId,
                          final Long tenantProjectId,
                          final TenantProjectDataDto tenantProjectData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId);
        request.setEntityId(tenantProjectId);
        return aliasModule.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantProjectData::setAliases)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectAliases(final Long tenantId,
                                 final Long tenantProjectId,
                                 final TenantProjectDataDto tenantProjectData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId);
        request.setUniquenessGroup(tenantProjectId);
        return aliasModule.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantProjectData::setProjectAliases)
                .replaceWithVoid();
    }
}
