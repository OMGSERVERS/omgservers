package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.getTenantDashboard;

import com.omgservers.model.dto.tenant.GetTenantDashboardRequest;
import com.omgservers.model.dto.tenant.GetTenantDashboardResponse;
import com.omgservers.model.tenantDashboard.TenantDashboardModel;
import com.omgservers.service.module.tenant.impl.operation.selectActiveProjectsByTenantId.SelectActiveProjectsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.selectActiveStagesByTenantId.SelectActiveStagesByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakersByTenantId.SelectActiveVersionMatchmakersByTenantId;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionProjectionsByTenantId.SelectActiveVersionProjectionsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionRuntimesByTenantId.SelectActiveVersionRuntimesByTenantId;
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
class GetTenantDashboardMethodImpl implements GetTenantDashboardMethod {

    final SelectActiveVersionProjectionsByTenantIdOperation selectActiveVersionProjectionsByTenantIdOperation;
    final SelectActiveProjectsByTenantIdOperation selectActiveProjectsByTenantIdOperation;
    final SelectActiveVersionMatchmakersByTenantId selectActiveVersionMatchmakersByTenantId;
    final SelectActiveVersionRuntimesByTenantId selectActiveVersionRuntimesByTenantId;
    final SelectActiveStagesByTenantIdOperation selectActiveStagesByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantDashboardResponse> getTenantDashboard(final GetTenantDashboardRequest request) {
        log.debug("Get tenant dashboard, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var tenantDashboard = new TenantDashboardModel();

                    return pgPool.withTransaction(sqlConnection ->
                            fillDashboard(sqlConnection, shard, tenantId, tenantDashboard));
                })
                .map(GetTenantDashboardResponse::new);
    }

    Uni<TenantDashboardModel> fillDashboard(final SqlConnection sqlConnection,
                                            final int shard,
                                            final Long tenantId,
                                            final TenantDashboardModel tenantDashboard) {
        return selectProjects(sqlConnection, shard, tenantId, tenantDashboard)
                .flatMap(voidItem -> selectStages(sqlConnection, shard, tenantId, tenantDashboard))
                .flatMap(voidItem -> selectVersions(sqlConnection, shard, tenantId, tenantDashboard))
                .flatMap(voidItem -> selectVersionRuntimes(sqlConnection, shard, tenantId, tenantDashboard))
                .flatMap(voidItem -> selectVersionMatchmakers(sqlConnection, shard, tenantId, tenantDashboard))
                .replaceWith(tenantDashboard);
    }

    Uni<Void> selectProjects(final SqlConnection sqlConnection,
                             final int shard,
                             final Long tenantId,
                             final TenantDashboardModel tenantDashboard) {
        return selectActiveProjectsByTenantIdOperation.selectActiveProjectsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDashboard::setProjects)
                .replaceWithVoid();
    }

    Uni<Void> selectStages(final SqlConnection sqlConnection,
                           final int shard,
                           final Long tenantId,
                           final TenantDashboardModel tenantDashboard) {
        return selectActiveStagesByTenantIdOperation.selectActiveStagesByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDashboard::setStages)
                .replaceWithVoid();
    }

    Uni<Void> selectVersions(final SqlConnection sqlConnection,
                             final int shard,
                             final Long tenantId,
                             final TenantDashboardModel tenantDashboard) {
        return selectActiveVersionProjectionsByTenantIdOperation.selectActiveVersionProjectionsByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDashboard::setVersions)
                .replaceWithVoid();
    }

    Uni<Void> selectVersionRuntimes(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final TenantDashboardModel tenantDashboard) {
        return selectActiveVersionRuntimesByTenantId.selectActiveVersionRuntimesByTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDashboard::setVersionRuntimes)
                .replaceWithVoid();
    }

    Uni<Void> selectVersionMatchmakers(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tenantId,
                                       final TenantDashboardModel tenantDashboard) {
        return selectActiveVersionMatchmakersByTenantId.selectActiveVersionMatchmakersTenantId(sqlConnection,
                        shard,
                        tenantId)
                .invoke(tenantDashboard::setVersionMatchmakers)
                .replaceWithVoid();
    }
}
