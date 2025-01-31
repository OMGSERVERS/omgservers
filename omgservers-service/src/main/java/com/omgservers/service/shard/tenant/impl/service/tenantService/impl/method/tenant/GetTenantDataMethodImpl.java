package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.module.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final SelectTenantOperation selectTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDataResponse> getTenantData(final GetTenantDataRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantData = new TenantDataDto();

                    return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                                    shard,
                                    tenantId,
                                    tenantData))
                            .flatMap(voidItem -> fillAliases(tenantId,
                                    tenantData))
                            .flatMap(voidItem -> fillTenantAliases(tenantId,
                                    tenantData))
                            .replaceWith(tenantData);
                })
                .map(GetTenantDataResponse::new);
    }

    Uni<Void> fillData(final SqlConnection sqlConnection,
                       final int shard,
                       final Long tenantId,
                       final TenantDataDto tenantData) {
        return fillTenant(sqlConnection, shard, tenantId, tenantData)
                .flatMap(voidItem -> fillTenantPermissions(sqlConnection, shard, tenantId, tenantData))
                .flatMap(voidItem -> fillProjects(sqlConnection, shard, tenantId, tenantData))
                .replaceWithVoid();
    }

    Uni<Void> fillTenant(final SqlConnection sqlConnection,
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
                .invoke(tenantData::setTenantProjects)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantId,
                          final TenantDataDto tenantData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(GlobalShardConfiguration.GLOBAL_SHARD_KEY);
        request.setEntityId(tenantId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantData::setAliases)
                .replaceWithVoid();
    }

    Uni<Void> fillTenantAliases(final Long tenantId,
                                final TenantDataDto tenantData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId);
        request.setUniquenessGroup(tenantId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantData::setTenantAliases)
                .replaceWithVoid();
    }
}
