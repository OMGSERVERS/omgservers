package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageDataResponse;
import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.SelectActiveTenantProjectsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectActiveTenantStagesByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectActiveTenantVersionProjectionsByTenantProjectIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectActiveTenantImageRefsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectActiveTenantLobbyRefsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.SelectActiveTenantMatchmakerRefsByTenantIdOperation;
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
class GetTenantStageDataMethodImpl implements GetTenantStageDataMethod {

    final SelectActiveTenantVersionProjectionsByTenantProjectIdOperation
            selectActiveTenantVersionProjectionsByTenantProjectIdOperation;
    final SelectActiveTenantMatchmakerRefsByTenantIdOperation selectActiveTenantMatchmakerRefsByTenantIdOperation;
    final SelectActiveTenantProjectsByTenantIdOperation selectActiveTenantProjectsByTenantIdOperation;
    final SelectActiveTenantImageRefsByTenantIdOperation selectActiveTenantImageRefsByTenantIdOperation;
    final SelectActiveTenantLobbyRefsByTenantIdOperation selectActiveTenantLobbyRefsByTenantIdOperation;
    final SelectActiveTenantStagesByTenantIdOperation selectActiveTenantStagesByTenantIdOperation;
    final SelectTenantStageOperation selectTenantStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantStageDataResponse> execute(final GetTenantStageDataRequest request) {
        log.debug("Get stage data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getTenantStageId();
                    final var stageData = new TenantStageDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, stageId, stageData));
                })
                .map(GetTenantStageDataResponse::new);
    }

    Uni<TenantStageDataDto> fillData(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final Long stageId,
                                     final TenantStageDataDto stageData) {
        return fillState(sqlConnection, shard, tenantId, stageId, stageData)
                .flatMap(voidItem -> fillVersionProjections(sqlConnection, shard, tenantId, stageId, stageData))
                .replaceWith(stageData);
    }

    Uni<Void> fillState(final SqlConnection sqlConnection,
                        final int shard,
                        final Long tenantId,
                        final Long stageId,
                        final TenantStageDataDto stageData) {
        return selectTenantStageOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        stageId)
                .invoke(stageData::setStage)
                .replaceWithVoid();
    }

    Uni<Void> fillVersionProjections(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long tenantId,
                                     final Long stageId,
                                     final TenantStageDataDto stageData) {
        return selectActiveTenantVersionProjectionsByTenantProjectIdOperation.execute(sqlConnection,
                        shard,
                        tenantId,
                        stageId)
                .invoke(stageData::setVersionProjections)
                .replaceWithVoid();
    }
}
