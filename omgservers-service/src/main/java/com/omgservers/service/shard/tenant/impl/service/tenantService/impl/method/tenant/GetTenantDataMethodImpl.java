package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.alias.ViewAliasesRequest;
import com.omgservers.schema.shard.alias.ViewAliasesResponse;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.shard.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.operation.alias.ViewPtrAliasesOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.impl.operation.tenant.SelectTenantOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantPermission.SelectActiveTenantPermissionsByTenantIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.SelectActiveTenantProjectsByTenantIdOperation;
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

    final AliasShard aliasShard;

    final SelectActiveTenantPermissionsByTenantIdOperation selectActiveTenantPermissionsByTenantIdOperation;
    final SelectActiveTenantProjectsByTenantIdOperation selectActiveTenantProjectsByTenantIdOperation;
    final ViewPtrAliasesOperation viewPtrAliasesOperation;
    final SelectTenantOperation selectTenantOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final ShardModel shardModel,
                                                    final GetTenantDataRequest request) {
        log.trace("{}", request);

        final int slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var tenantData = new TenantDataDto();

        return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                        slot,
                        tenantId,
                        tenantData))
                .flatMap(voidItem -> fillAliases(tenantId,
                        tenantData))
                .flatMap(voidItem -> fillTenantAliases(tenantId,
                        tenantData))
                .replaceWith(tenantData)
                .map(GetTenantDataResponse::new);
    }

    Uni<Void> fillData(final SqlConnection sqlConnection,
                       final int slot,
                       final Long tenantId,
                       final TenantDataDto tenantData) {
        return fillTenant(sqlConnection, slot, tenantId, tenantData)
                .flatMap(voidItem -> fillTenantPermissions(sqlConnection, slot, tenantId, tenantData))
                .flatMap(voidItem -> fillProjects(sqlConnection, slot, tenantId, tenantData))
                .replaceWithVoid();
    }

    Uni<Void> fillTenant(final SqlConnection sqlConnection,
                         final int slot,
                         final Long tenantId,
                         final TenantDataDto tenantDetails) {
        return selectTenantOperation.execute(sqlConnection,
                        slot,
                        tenantId)
                .invoke(tenantDetails::setTenant)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantPermissions(final SqlConnection sqlConnection,
                                    final int slot,
                                    final Long tenantId,
                                    final TenantDataDto tenantData) {
        return selectActiveTenantPermissionsByTenantIdOperation.execute(sqlConnection,
                        slot,
                        tenantId)
                .invoke(tenantData::setTenantPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillProjects(final SqlConnection sqlConnection,
                           final int slot,
                           final Long tenantId,
                           final TenantDataDto tenantData) {
        return selectActiveTenantProjectsByTenantIdOperation.execute(sqlConnection,
                        slot,
                        tenantId)
                .invoke(tenantData::setTenantProjects)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantId,
                          final TenantDataDto tenantData) {
        return viewPtrAliasesOperation.execute(tenantId)
                .invoke(tenantData::setAliases)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantAliases(final Long tenantId,
                                final TenantDataDto tenantData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId.toString());
        request.setUniquenessGroup(tenantId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantData::setTenantAliases)
                .replaceWithVoid();
    }
}
