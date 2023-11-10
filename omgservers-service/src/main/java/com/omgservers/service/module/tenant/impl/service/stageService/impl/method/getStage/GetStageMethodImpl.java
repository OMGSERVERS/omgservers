package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.getStage;

import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.service.module.tenant.impl.operation.selectStage.SelectStageOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetStageMethodImpl implements GetStageMethod {

    final SelectStageOperation selectStageOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetStageResponse> getStage(final GetStageRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectStageOperation
                            .selectStage(sqlConnection, shardModel.shard(), tenantId, id));
                })
                .map(GetStageResponse::new);
    }
}
