package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.getStageMethod;

import com.omgservers.application.module.tenantModule.impl.operation.selectStageOperation.SelectStageOperation;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
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
    public Uni<GetStageInternalResponse> getStage(final GetStageInternalRequest request) {
        GetStageInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectStageOperation
                            .selectStage(sqlConnection, shardModel.shard(), id));
                })
                .map(GetStageInternalResponse::new);
    }
}
