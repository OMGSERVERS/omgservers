package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.getStageData;

import com.omgservers.schema.module.tenant.stage.GetStageDataRequest;
import com.omgservers.schema.module.tenant.stage.GetStageDataResponse;
import com.omgservers.schema.module.tenant.stage.dto.StageDataDto;
import com.omgservers.service.module.tenant.impl.operation.project.selectActiveProjectsByTenantId.SelectActiveProjectsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByTenantId.SelectActiveStagesByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.stage.selectStage.SelectStageOperation;
import com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionProjectionsByStageId.SelectActiveVersionProjectionsByStageIdOperation;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByTenantId.SelectActiveVersionImageRefsByTenantId;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectActiveVersionLobbyRefsByTenantId.SelectActiveVersionLobbyRefsByTenantId;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectActiveVersionMatchmakerRefsByTenantId.SelectActiveVersionMatchmakerRefsByTenantId;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetStageDataMethodImpl implements GetStageDataMethod {

    final SelectActiveVersionProjectionsByStageIdOperation selectActiveVersionProjectionsByStageIdOperation;
    final SelectActiveVersionMatchmakerRefsByTenantId selectActiveVersionMatchmakerRefsByTenantId;
    final SelectActiveProjectsByTenantIdOperation selectActiveProjectsByTenantIdOperation;
    final SelectActiveVersionImageRefsByTenantId selectActiveVersionImageRefsByTenantId;
    final SelectActiveVersionLobbyRefsByTenantId selectActiveVersionLobbyRefsByTenantId;
    final SelectActiveStagesByTenantIdOperation selectActiveStagesByTenantIdOperation;
    final SelectStageOperation selectStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetStageDataResponse> getStageData(final GetStageDataRequest request) {
        log.debug("Get stage data, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final int shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    final var stageData = new StageDataDto();

                    return pgPool.withTransaction(sqlConnection ->
                            fillData(sqlConnection, shard, tenantId, stageId, stageData));
                })
                .map(GetStageDataResponse::new);
    }

    Uni<StageDataDto> fillData(final SqlConnection sqlConnection,
                               final int shard,
                               final Long tenantId,
                               final Long stageId,
                               final StageDataDto stageData) {
        return fillState(sqlConnection, shard, tenantId, stageId, stageData)
                .flatMap(voidItem -> fillVersionProjections(sqlConnection, shard, tenantId, stageId, stageData))
                .replaceWith(stageData);
    }

    Uni<Void> fillState(final SqlConnection sqlConnection,
                        final int shard,
                        final Long tenantId,
                        final Long stageId,
                        final StageDataDto stageData) {
        return selectStageOperation.selectStage(sqlConnection,
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
                                     final StageDataDto stageData) {
        return selectActiveVersionProjectionsByStageIdOperation.selectActiveVersionProjectionsByStageId(sqlConnection,
                        shard,
                        tenantId,
                        stageId)
                .invoke(stageData::setVersionProjections)
                .replaceWithVoid();
    }
}
