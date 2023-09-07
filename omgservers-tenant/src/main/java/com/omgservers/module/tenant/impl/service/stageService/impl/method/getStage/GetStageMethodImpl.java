package com.omgservers.module.tenant.impl.service.stageService.impl.method.getStage;

import com.omgservers.module.tenant.impl.operation.selectStage.SelectStageOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
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
        GetStageRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectStageOperation
                            .selectStage(sqlConnection, shardModel.shard(), id));
                })
                .map(GetStageResponse::new);
    }
}
