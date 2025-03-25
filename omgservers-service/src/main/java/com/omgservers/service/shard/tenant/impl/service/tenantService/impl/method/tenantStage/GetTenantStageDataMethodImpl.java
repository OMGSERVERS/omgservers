package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageDataResponse> execute(final GetTenantStageDataRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantStageId = request.getTenantStageId();
                    final var tenantStageData = new TenantStageDataDto();

                    return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                                    shard,
                                    tenantId,
                                    tenantStageId,
                                    tenantStageData))
                            .flatMap(voidItem -> fillAliases(tenantId,
                                    tenantStageId,
                                    tenantStageData))
                            .replaceWith(tenantStageData);
                })
                .map(GetTenantStageDataResponse::new);
    }

    Uni<TenantStageDataDto> fillData(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final Long tenantStageId,
                                     final TenantStageDataDto tenantStageData) {
        return fillStage(sqlConnection, shard, tenantId, tenantStageId, tenantStageData)
                .flatMap(voidItem -> fillPermissions(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId,
                        tenantStageData))
                .flatMap(voidItem -> fillDeployments(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId,
                        tenantStageData))
                .replaceWith(tenantStageData);
    }

    Uni<Void> fillStage(final SqlConnection sqlConnection,
                        final int shard,
                        final Long tenantId,
                        final Long tenantStageId,
                        final TenantStageDataDto tenantStageData) {
        return selectTenantStageOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(tenantStageData::setStage)
                .replaceWithVoid();
    }

    Uni<Void> fillPermissions(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long tenantStageId,
                              final TenantStageDataDto stageData) {
        return selectActiveTenantStagePermissionsByTenantStageIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setPermissions)
                .replaceWithVoid();
    }

    Uni<Void> fillDeployments(final SqlConnection sqlConnection,
                              final int shard,
                              final Long tenantId,
                              final Long tenantStageId,
                              final TenantStageDataDto stageData) {
        return selectActiveTenantDeploymentResourcesByStageIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        tenantStageId)
                .invoke(stageData::setDeployments)
                .replaceWithVoid();
    }

    Uni<Void> fillAliases(final Long tenantId,
                          final Long tenantStageId,
                          final TenantStageDataDto tenantStageData) {
        final var request = new ViewAliasesRequest();
        request.setShardKey(tenantId);
        request.setEntityId(tenantStageId);
        return aliasShard.getService().execute(request)
                .map(ViewAliasesResponse::getAliases)
                .invoke(tenantStageData::setAliases)
                .replaceWithVoid();
    }
}
