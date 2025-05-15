package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantProject;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectDataResponse;
import com.omgservers.schema.shard.tenant.tenantProject.dto.TenantProjectDataDto;
import com.omgservers.service.operation.alias.ViewPtrAliasesOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.SelectActiveTenantProjectPermissionsByTenantProjectIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantProjectIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.SelectActiveTenantVersionProjectionsByTenantProjectIdOperation;
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

    final AliasShard aliasShard;

    final SelectActiveTenantProjectPermissionsByTenantProjectIdOperation
            selectActiveTenantProjectPermissionsByTenantProjectIdOperation;
    final SelectActiveTenantVersionProjectionsByTenantProjectIdOperation
            selectActiveTenantVersionProjectionsByTenantProjectIdOperation;
    final SelectActiveTenantStagesByTenantProjectIdOperation
            selectActiveTenantStagesByTenantProjectIdOperation;
    final SelectTenantProjectOperation selectTenantProjectOperation;

    final ViewPtrAliasesOperation viewPtrAliasesOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantProjectDataResponse> execute(final ShardModel shardModel,
                                                     final GetTenantProjectDataRequest request) {
        log.debug("{}", request);

        final int slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var tenantProjectData = new TenantProjectDataDto();
        return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillAliases(tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectAliases(tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .replaceWith(tenantProjectData)
                .map(GetTenantProjectDataResponse::new);
    }

    Uni<Void> fillData(final SqlConnection sqlConnection,
                       final int slot,
                       final Long tenantId,
                       final Long tenantProjectId,
                       final TenantProjectDataDto tenantProjectData) {
        return fillProject(sqlConnection, slot, tenantId, tenantProjectId, tenantProjectData)
                .flatMap(voidItem -> fillAliases(tenantProjectId, tenantProjectData))
                .flatMap(voidItem -> fillProjectPermissions(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectStages(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectVersions(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .flatMap(voidItem -> fillProjectAliases(tenantId,
                        tenantProjectId,
                        tenantProjectData))
                .replaceWithVoid();
    }

    Uni<Void> fillProject(final SqlConnection sqlConnection,
                          final int slot,
                          final Long tenantId,
                          final Long tenantProjectId,
                          final TenantProjectDataDto tenantProjectData) {
        return selectTenantProjectOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProject)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectPermissions(final SqlConnection sqlConnection,
                                     final int slot,
                                     final Long tenantId,
                                     final Long tenantProjectId,
                                     final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantProjectPermissionsByTenantProjectIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectStages(final SqlConnection sqlConnection,
                                final int slot,
                                final Long tenantId,
                                final Long tenantProjectId,
                                final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantStagesByTenantProjectIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectStages)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectVersions(final SqlConnection sqlConnection,
                                  final int slot,
                                  final Long tenantId,
                                  final Long tenantProjectId,
                                  final TenantProjectDataDto tenantProjectData) {
        return selectActiveTenantVersionProjectionsByTenantProjectIdOperation.execute(sqlConnection,
                        slot,
                        tenantId,
                        tenantProjectId)
                .invoke(tenantProjectData::setProjectVersions)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantProjectId,
                          final TenantProjectDataDto tenantProjectData) {
        return viewPtrAliasesOperation.execute(tenantProjectId)
                .invoke(tenantProjectData::setAliases)
                .replaceWithVoid();
    }

    Uni<Void> fillProjectAliases(final Long tenantId,
                                 final Long tenantProjectId,
                                 final TenantProjectDataDto tenantProjectData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId.toString());
        request.setUniquenessGroup(tenantProjectId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantProjectData::setProjectAliases)
                .replaceWithVoid();
    }
}
